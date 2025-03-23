package com.fujitsu.trialtask.service;

import com.fujitsu.trialtask.model.WeatherCondition;
import com.fujitsu.trialtask.repository.WeatherConditionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class WeatherConditionServiceTest {
    private WeatherConditionRepository weatherConditionRepository;
    private WeatherConditionService weatherConditionService;

    @BeforeEach
    void setUp() {
        weatherConditionRepository = mock(WeatherConditionRepository.class);
        weatherConditionService = new WeatherConditionService(weatherConditionRepository);
    }

    @Test
    void insertWeatherConditions_shouldSaveAllAndReturnSavedList() {
        WeatherCondition w1 = new WeatherCondition();
        w1.setStationName("Tallinn");
        WeatherCondition w2 = new WeatherCondition();
        w2.setStationName("Tartu");

        List<WeatherCondition> inputList = List.of(w1, w2);

        when(weatherConditionRepository.saveAll(inputList)).thenReturn(inputList);
        List<WeatherCondition> result = weatherConditionService.insertWeatherConditions(inputList);

        assertThat(result).isEqualTo(inputList);
        verify(weatherConditionRepository, times(1)).saveAll(inputList);
    }
}
