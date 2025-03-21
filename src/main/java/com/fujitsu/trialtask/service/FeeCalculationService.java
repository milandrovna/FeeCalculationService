package com.fujitsu.trialtask.service;

import com.fujitsu.trialtask.model.WeatherCondition;
import com.fujitsu.trialtask.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class FeeCalculationService {

    private final ActiveRegionRepository activeRegionRepository;
    private final WeatherConditionRepository weatherConditionRepository;
    private final WeatherPhenomenonRepository weatherPhenomenonRepository;
    private final BusinessRuleRepository businessRuleRepository;
    private final VehicleRepository vehicleRepository;


    public float calculateFee(String region, String vehicle){

        Optional<WeatherCondition> latestWeatherOptional = getLatestWeatherForRegion(region);

        if(latestWeatherOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Weather data not available for chosen region");
        }

        WeatherCondition weather = latestWeatherOptional.get();

        float airTemperature = weather.getAirTemperature();
        float windSpeed = weather.getWindSpeed();
        String weatherPhenomenon = weather.getWeatherPhenomenon();

        List<String> metrics = businessRuleRepository.findMetricTypesByVehicleType(vehicle);

        System.out.println(metrics);
        float additionalFee = 0;

        if (metrics.contains("Air Temperature")){
            additionalFee += getFeeForAirTemperature(airTemperature);
        }
        if(metrics.contains("Wind Speed")){
            additionalFee += getFeeForWindSpeed(windSpeed);
        }
        if(metrics.contains("Weather Phenomenon")){
            additionalFee += getFeeForWeatherPhenomenon(weatherPhenomenon);
        }

        additionalFee += vehicleRepository.findRegionalBaseFeeByVehicleTypeRegionName(vehicle, region);

        return additionalFee;

    }

    private float getFeeForWeatherPhenomenon(String weatherPhenomenon) {

        Optional<Boolean> prohobited = weatherPhenomenonRepository.findProhibitionByPhenomenonType(weatherPhenomenon);

        if(prohobited.isPresent() && prohobited.get()){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usage of selected vehicle type is forbidden");
        }

        Optional<Float> additionalFee = weatherPhenomenonRepository.findFeeByPhenomenonType(weatherPhenomenon);

        return additionalFee.orElse(0F);
    }

    private float getFeeForWindSpeed(float windSpeed) {

        if (windSpeed > 20) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usage of selected vehicle type is forbidden");
        }

        Optional<Float> additionalWindFee = businessRuleRepository.findFeeByWindSpeed(windSpeed);
        return additionalWindFee.orElse(0F);

    }

    private float getFeeForAirTemperature(float airTemperature) {

        Optional<Float> additionalTemperatureFee = businessRuleRepository.findFeeByAirTemperature(airTemperature);
        return additionalTemperatureFee.orElse(0F);

    }

    private Optional<WeatherCondition> getLatestWeatherForRegion(String region) {

        String stationName = activeRegionRepository.findStationNameByRegionName(region);


        return weatherConditionRepository.findLatestWeatherByStation(stationName);

    }

    private int getAdditionalFee(String metric, String vehicle, int metricValue){return 0;}

}
