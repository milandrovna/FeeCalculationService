package com.fujitsu.trialtask.mapper;

import com.fujitsu.trialtask.dto.ActiveRegionDto;
import com.fujitsu.trialtask.model.ActiveRegion;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ActiveRegionMapper {

    ActiveRegionDto entityToDto(ActiveRegion entity);

    ActiveRegion dtoToEntity(ActiveRegionDto dto);

    List<ActiveRegionDto> entitiesToDtoList(List<ActiveRegion> entities);

    List<ActiveRegion> dtosToEntityList(List<ActiveRegionDto> dtos);
}
