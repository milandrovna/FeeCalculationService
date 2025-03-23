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
    private final RegionalBaseFeeRepository regionalBaseFeeRepository;


    /**
     * Calculates the delivery fee based on region, vehicle type, and optionally a timestamp.
     * If a timestamp is provided, weather conditions at that time are used
     * or at the closest timestamp that is less than provided timestamp;
     *
     * otherwise, the latest weather data is applied.
     * The fee is calculated using applicable business rules and regional base fees.
     *
     * @param region the name of the delivery region
     * @param vehicle the type of vehicle used for delivery
     * @param timestamp optional timestamp to calculate fee based on historical weather
     * @return the calculated delivery fee as a float
     * @throws ResponseStatusException if no weather data is available for the region
     * or no data available at/close to provided timestamp
     */

    public float calculateFee(String region, String vehicle, Long timestamp){

        Optional<WeatherCondition> latestWeatherOptional = (timestamp != null)
                ? getWeatherForRegionAtTime(region, timestamp)
                : getLatestWeatherForRegion(region);

        if(latestWeatherOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Weather data not available for chosen region");
        }

        WeatherCondition weather = latestWeatherOptional.get();

        float airTemperature = weather.getAirTemperature();
        float windSpeed = weather.getWindSpeed();
        String weatherPhenomenon = weather.getWeatherPhenomenon().toLowerCase();

        List<String> metrics = businessRuleRepository.findMetricTypesByVehicleType(vehicle);

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

        additionalFee += regionalBaseFeeRepository.findRegionalBaseFeeByVehicleTypeRegionName(vehicle, region);

        return additionalFee;

    }

    /**
     * Finds fee for current weather phenomenon
     * @param weatherPhenomenon current weather phenomenon
     * @throws ResponseStatusException if phenomenon forbids delivery
     * @return fee for given weather phenomenon
     */
    private float getFeeForWeatherPhenomenon(String weatherPhenomenon) {

        Optional<Boolean> prohobited = weatherPhenomenonRepository.findProhibitionByPhenomenonType(weatherPhenomenon);

        if(prohobited.isPresent() && prohobited.get()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usage of selected vehicle type is forbidden");
        }

        Optional<Float> additionalFee = weatherPhenomenonRepository.findFeeByPhenomenonType(weatherPhenomenon);

        return additionalFee.orElse(0F);
    }

    /**
     * Finds fee for current wind speed
     * @param windSpeed current wind speed
     * @throws ResponseStatusException if wind speed forbids delivery
     * @return fee for given wind speed
     */
    private float getFeeForWindSpeed(float windSpeed) {

        Optional<Float> additionalWindFee = businessRuleRepository.findFeeByWindSpeed(windSpeed);

        if(additionalWindFee.isPresent()){
            return additionalWindFee.get();
        }
        else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usage of selected vehicle type is forbidden");
    }

    /**
     * Finds fee for current air temperature
     * @param airTemperature current air temperature
     * @return fee for given air temperature
     */
    private float getFeeForAirTemperature(float airTemperature) {

        Optional<Float> additionalTemperatureFee = businessRuleRepository.findFeeByAirTemperature(airTemperature);
        return additionalTemperatureFee.orElse(0F);

    }

    /**
     * Additional method to find weather condition for provided region
     * at given timestamp or close to timestamp (<= timestamp)
     *
     * @param region the name of the delivery region
     * @param timestamp timestamp to calculate fee based on historical weather
     * @return weather conditions at given timestamp
     */
    private Optional<WeatherCondition> getWeatherForRegionAtTime(String region, Long timestamp) {
        String stationName = activeRegionRepository.findStationNameByRegionName(region);

        return weatherConditionRepository.findByStationAndTimestamp(stationName, timestamp);
    }

    /**
     * Retrieves latest information about weather conditions
     * @param region the name of the delivery region
     * @return latest weather conditions
     */
    private Optional<WeatherCondition> getLatestWeatherForRegion(String region) {

        String stationName = activeRegionRepository.findStationNameByRegionName(region);

        return weatherConditionRepository.findLatestWeatherByStation(stationName);

    }



}
