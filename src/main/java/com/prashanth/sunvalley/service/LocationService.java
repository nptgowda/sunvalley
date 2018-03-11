package com.prashanth.sunvalley.service;

import com.prashanth.sunvalley.Model.LocationDTO;
import com.prashanth.sunvalley.Model.LocationListDTO;
import com.prashanth.sunvalley.Model.StudentDTO;

import java.util.List;

public interface LocationService {

    List<LocationDTO> getAllLocations();
    LocationDTO getLocationById(Long id);
    LocationDTO createLocation(LocationDTO locationDTO);
    LocationDTO updateLocation(Long id,LocationDTO locationDTO);
    void deleteLocationById(Long id);

    List<StudentDTO> getAllStudentsOfLocationById(Long id);

    List<LocationDTO> createLocations(LocationListDTO locationListDTO);
}
