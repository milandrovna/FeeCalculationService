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

    public List<WeatherCondition> insertWeatherConditions(List<WeatherCondition> weatherConditions) {
        return weatherConditionRepository.saveAll(weatherConditions);
    }
}
