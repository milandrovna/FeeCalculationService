package com.fujitsu.trialtask.mapper;

import com.fujitsu.trialtask.dto.WeatherConditionDto;
import com.fujitsu.trialtask.model.WeatherCondition;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")public interface WeatherConditionMapper {
    WeatherConditionDto entityToDto(WeatherCondition entity);

    WeatherCondition dtoToEntity(WeatherConditionDto dto);

    List<WeatherConditionDto> entitiesToDtoList(List<WeatherCondition> entities);

    List<WeatherCondition> dtosToEntityList(List<WeatherConditionDto> dtos);
}
