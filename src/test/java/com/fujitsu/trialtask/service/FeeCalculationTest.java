package com.fujitsu.trialtask.service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.fujitsu.trialtask.model.WeatherCondition;
import com.fujitsu.trialtask.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class FeeCalculationTest {

    @Mock
    private ActiveRegionRepository activeRegionRepository;

    @Mock
    private WeatherConditionRepository weatherConditionRepository;

    @Mock
    private WeatherPhenomenonRepository weatherPhenomenonRepository;

    @Mock
    private BusinessRuleRepository businessRuleRepository;

    @Mock
    private RegionalBaseFeeRepository regionalBaseFeeRepository;

    @InjectMocks
    private FeeCalculationService feeCalculationService;

    /**
     * Test the TARTU and BIKE scenario:
     * - Regional Base Fee (RBF) for BIKE in TARTU: 2.5 €
     * - Air Temperature = -2.1°C -> Air Temperature Extra Fee (ATEF) = 0.5 €
     * - Wind Speed = 4.7 m/s -> Wind Speed Extra Fee (WSEF) = 0 €
     * - Weather Phenomenon "Light snow shower" -> Weather Phenomenon Extra Fee (WPEF) = 1 €
     * Total fee should be: 2.5 + 0.5 + 0 + 1 = 4.0 €
     */
    @Test
    public void testCalculateFeeForTartuBike() {

        when(activeRegionRepository.findStationNameByRegionName("TARTU"))
                .thenReturn("Tartu-Tõravere");

        WeatherCondition weather = new WeatherCondition();
        weather.setAirTemperature(-2.1f);
        weather.setWindSpeed(4.7f);
        weather.setWeatherPhenomenon("Light snow shower");
        when(weatherConditionRepository.findLatestWeatherByStation("Tartu-Tõravere"))
                .thenReturn(Optional.of(weather));

        when(businessRuleRepository.findMetricTypesByVehicleType("BIKE"))
                .thenReturn(Arrays.asList("Air Temperature", "Wind Speed", "Weather Phenomenon"));

        when(businessRuleRepository.findFeeByAirTemperature(-2.1f))
                .thenReturn(Optional.of(0.5f));

        when(businessRuleRepository.findFeeByWindSpeed(4.7f))
                .thenReturn(Optional.of(0f));

        when(weatherPhenomenonRepository.findProhibitionByPhenomenonType("light snow shower"))
                .thenReturn(Optional.of(false));
        when(weatherPhenomenonRepository.findFeeByPhenomenonType("light snow shower"))
                .thenReturn(Optional.of(1f));

        when(regionalBaseFeeRepository.findRegionalBaseFeeByVehicleTypeRegionName("BIKE", "TARTU"))
                .thenReturn(2.5f);

        float fee = feeCalculationService.calculateFee("TARTU", "BIKE", null);

        assertEquals(4.0f, fee, 0.0001f);
    }

    @Test
    public void testCalculateFeeNoWeatherData() {
        when(activeRegionRepository.findStationNameByRegionName("TALLINN"))
                .thenReturn("Tallinn-Harku");

        when(weatherConditionRepository.findLatestWeatherByStation("Tallinn-Harku"))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            feeCalculationService.calculateFee("TALLINN", "Car", null);
        });
        assertEquals("Weather data not available for chosen region", exception.getReason());
    }

    @Test
    public void testCalculateFeeHighWindSpeedThrowsException() {

        when(activeRegionRepository.findStationNameByRegionName("TALLINN"))
                .thenReturn("Tallinn-Harku");


        WeatherCondition weather = new WeatherCondition();
        weather.setAirTemperature(10f);
        weather.setWindSpeed(25f);
        weather.setWeatherPhenomenon("Clear");
        when(weatherConditionRepository.findLatestWeatherByStation("Tallinn-Harku"))
                .thenReturn(Optional.of(weather));

        when(businessRuleRepository.findMetricTypesByVehicleType("Scooter"))
                .thenReturn(Arrays.asList("Wind Speed"));

        when(businessRuleRepository.findFeeByWindSpeed(25f))
                .thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            feeCalculationService.calculateFee("TALLINN", "Scooter", null);
        });
        assertEquals("Usage of selected vehicle type is forbidden", exception.getReason());
    }

    @Test
    public void testCalculateFeeProhibitedWeatherPhenomenonThrowsException() {

        when(activeRegionRepository.findStationNameByRegionName("PÄRNU"))
                .thenReturn("Pärnu");

        WeatherCondition weather = new WeatherCondition();
        weather.setAirTemperature(5f);
        weather.setWindSpeed(5f);
        weather.setWeatherPhenomenon("Light Hail");
        when(weatherConditionRepository.findLatestWeatherByStation("Pärnu"))
                .thenReturn(Optional.of(weather));

        when(businessRuleRepository.findMetricTypesByVehicleType("Car"))
                .thenReturn(Arrays.asList("Weather Phenomenon"));

        when(weatherPhenomenonRepository.findProhibitionByPhenomenonType("light hail"))
                .thenReturn(Optional.of(true));


        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            feeCalculationService.calculateFee("PÄRNU", "Car", null);
        });
        assertEquals("Usage of selected vehicle type is forbidden", exception.getReason());
    }
    @Test
    void testCalculateFeeWithTimestamp_shouldUseWeatherAtTimestamp() {
        long timestamp = 1679856000L;

        when(activeRegionRepository.findStationNameByRegionName("TARTU"))
                .thenReturn("Tartu-Tõravere");

        WeatherCondition weather = new WeatherCondition();
        weather.setAirTemperature(0f);
        weather.setWindSpeed(5f);
        weather.setWeatherPhenomenon("Light snow");

        when(weatherConditionRepository.findByStationAndTimestamp("Tartu-Tõravere", timestamp))
                .thenReturn(Optional.of(weather));

        when(businessRuleRepository.findMetricTypesByVehicleType("BIKE"))
                .thenReturn(List.of("Air Temperature", "Wind Speed", "Weather Phenomenon"));

        when(businessRuleRepository.findFeeByAirTemperature(0f))
                .thenReturn(Optional.of(0.0f));

        when(businessRuleRepository.findFeeByWindSpeed(5f))
                .thenReturn(Optional.of(0.5f));

        when(weatherPhenomenonRepository.findProhibitionByPhenomenonType("light snow"))
                .thenReturn(Optional.of(false));

        when(weatherPhenomenonRepository.findFeeByPhenomenonType("light snow"))
                .thenReturn(Optional.of(1.0f));

        when(regionalBaseFeeRepository.findRegionalBaseFeeByVehicleTypeRegionName("BIKE", "TARTU"))
                .thenReturn(2.5f);

        float fee = feeCalculationService.calculateFee("TARTU", "BIKE", timestamp);

        assertEquals(4.0f, fee, 0.0001f);
    }

}
