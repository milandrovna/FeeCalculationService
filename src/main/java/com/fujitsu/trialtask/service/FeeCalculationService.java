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


    public float calculateFee(String region, String vehicle, Long timestamp){

        Optional<WeatherCondition> latestWeatherOptional = (timestamp != null)
                ? getWeatherForRegionAtTime(region, timestamp)
                : getLatestWeatherForRegion(region);


        if(latestWeatherOptional.isEmpty()){
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

    private Optional<WeatherCondition> getWeatherForRegionAtTime(String region, Long timestamp) {
        String stationName = activeRegionRepository.findStationNameByRegionName(region);

        return weatherConditionRepository.findByStationAndTimestamp(stationName, timestamp);
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

        Optional<Float> additionalWindFee = businessRuleRepository.findFeeByWindSpeed(windSpeed);

        if(additionalWindFee.isPresent()){
            return additionalWindFee.get();
        }
        else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usage of selected vehicle type is forbidden");
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
