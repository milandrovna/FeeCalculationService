package com.fujitsu.trialtask.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.*;

import com.fujitsu.trialtask.dto.ObservationsDto;
import com.fujitsu.trialtask.dto.WeatherConditionDto;
import com.fujitsu.trialtask.mapper.WeatherConditionMapper;
import com.fujitsu.trialtask.model.WeatherCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import org.mockito.MockedStatic;

@ExtendWith(MockitoExtension.class)
class WeatherConditionServiceTest {


    @Mock
    private ActiveRegionService activeRegionService;

    @Mock
    private WeatherConditionMapper weatherConditionMapper;

    @Mock
    private WeatherConditionService weatherConditionService;

    @InjectMocks
    private WeatherConditionRequestService scheduler;

    @Mock
    private RestTemplate mockRestTemplate;

    @BeforeEach
    void injectRestTemplateViaReflection() throws Exception {
        // Inject the mocked RestTemplate into the private final field
        Field restTemplateField = WeatherConditionRequestService.class.getDeclaredField("restTemplate");
        restTemplateField.setAccessible(true);
        restTemplateField.set(scheduler, mockRestTemplate);
    }

    @Test
    void testGetWeatherData() {
        Set<String> activeStations = Set.of("Tallinn-Harku", "Tartu-Tõravere");
        when(activeRegionService.getActiveStationNames()).thenReturn(activeStations);

        String fakeXml = "<observations>mocked</observations>";
        when(mockRestTemplate.getForObject(anyString(), eq(String.class))).thenReturn(fakeXml);

        WeatherConditionDto dto1 = new WeatherConditionDto();
        dto1.setStationName("Tallinn-Harku");

        WeatherConditionDto dto2 = new WeatherConditionDto();
        dto2.setStationName("Pärnu");

        WeatherConditionDto dto3 = new WeatherConditionDto();
        dto3.setStationName("Tartu-Tõravere");


        List<WeatherConditionDto> stations = List.of(dto1, dto2, dto3);

        WeatherCondition mappedCondition = new WeatherCondition();
        WeatherCondition mappedCondition2 = new WeatherCondition();

        ObservationsDto mockedObservations = mock(ObservationsDto.class);
        when(mockedObservations.getStations()).thenReturn(stations);

        try (MockedStatic<ObservationsDto> mockedStatic = mockStatic(ObservationsDto.class)) {
            mockedStatic.when(() -> ObservationsDto.convertXmlToObservationsDto(fakeXml))
                    .thenReturn(mockedObservations);

            when(weatherConditionMapper.dtoToEntity(dto1)).thenReturn(mappedCondition);
            when(weatherConditionMapper.dtoToEntity(dto3)).thenReturn(mappedCondition2);

            scheduler.getWeatherData();

            verify(weatherConditionMapper).dtoToEntity(dto1);
            verify(weatherConditionMapper).dtoToEntity(dto3);
            verify(weatherConditionMapper, never()).dtoToEntity(dto2);
            verify(weatherConditionService).insertWeatherConditions(List.of(mappedCondition,  mappedCondition2));
        }
    }

}
