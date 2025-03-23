package com.fujitsu.trialtask.service;

import com.fujitsu.trialtask.dto.WeatherConditionDto;
import com.fujitsu.trialtask.dto.ObservationsDto;
import com.fujitsu.trialtask.mapper.WeatherConditionMapper;
import com.fujitsu.trialtask.model.WeatherCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherConditionRequestService {

    private final ActiveRegionService activeRegionService;

    private final WeatherConditionMapper weatherConditionMapper;

    private final WeatherConditionService weatherConditionService;

    private final RestTemplate restTemplate = new RestTemplate();

    private final String url = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

    /***
     *
     */

    @Scheduled(cron = "0 15 * * * *")
    public void getWeatherData(){

        Set<String> activeStationNames =  activeRegionService.getActiveStationNames();

        String xmlContent = restTemplate.getForObject(url, String.class);

        ObservationsDto observationsDto = ObservationsDto.convertXmlToObservationsDto(xmlContent);

        List<WeatherCondition> weatherData = new ArrayList<>();

        for (WeatherConditionDto weatherConditionDto : observationsDto.getStations()){
            if (activeStationNames.contains(weatherConditionDto.getStationName())){
                weatherData.add(weatherConditionMapper.dtoToEntity(weatherConditionDto));
            }
        }

        weatherConditionService.insertWeatherConditions(weatherData);
    }

}
