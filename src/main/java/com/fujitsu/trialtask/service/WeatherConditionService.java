package com.fujitsu.trialtask.service;

import com.fujitsu.trialtask.model.WeatherCondition;
import com.fujitsu.trialtask.repository.WeatherConditionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherConditionService {

    private final WeatherConditionRepository weatherConditionRepository;

    /**
     * Saves a list of weather conditions to the database.
     * Used for bulk inserting weather data from external sources.
     *
     * @param weatherConditions list of weather condition entities to be saved
     * @return list of saved weather condition entities
     */
    public List<WeatherCondition> insertWeatherConditions(List<WeatherCondition> weatherConditions) {
        return weatherConditionRepository.saveAll(weatherConditions);
    }
}
