package com.fujitsu.trialtask.service;

import com.fujitsu.trialtask.model.ActiveRegion;
import com.fujitsu.trialtask.model.WeatherCondition;
import com.fujitsu.trialtask.repository.ActiveRegionRepository;
import com.fujitsu.trialtask.repository.WeatherConditionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeeCalculationService {

    private final ActiveRegionRepository activeRegionRepository;
    private final WeatherConditionRepository weatherConditionRepository;


    private int calculateFee(String region, String vehicle){

        Optional<WeatherCondition> latestWeatherOptional = getLatestWeatherForRegion(region);

        if(latestWeatherOptional.isEmpty()){
            throw new RuntimeException("Weather data not available for region: " + region);
        }

        WeatherCondition weather = latestWeatherOptional.get();

        double airTemperature = weather.getAirTemperature();
        double windSpeed = weather.getWindSpeed();
        String weatherPhenomenon = weather.getWeatherPhenomenon();




        return 0;


    }

    private Optional<WeatherCondition> getLatestWeatherForRegion(String region) {

        Optional<String> stationNameOptional = activeRegionRepository.findStationNameByRegion(region);

        if (stationNameOptional.isEmpty()) {
            throw new RuntimeException("No weather station found for region: " + region);
        }

        String stationName = stationNameOptional.get();

        return weatherConditionRepository.findLatestWeatherByStation(stationName);
    }


}
