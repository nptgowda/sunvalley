package com.prashanth.sunvalley.mapper;

import com.prashanth.sunvalley.Model.LocationDTO;
import com.prashanth.sunvalley.domain.Location;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocationMapper {
    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    LocationDTO locationToLocationDTO(Location location);
    Location locationDTOToLocation(LocationDTO locationDTO);
}
