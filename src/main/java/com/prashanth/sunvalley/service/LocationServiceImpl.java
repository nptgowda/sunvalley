package com.prashanth.sunvalley.service;

import com.prashanth.sunvalley.Model.LocationDTO;
import com.prashanth.sunvalley.Model.LocationListDTO;
import com.prashanth.sunvalley.Model.StudentDTO;
import com.prashanth.sunvalley.domain.Location;
import com.prashanth.sunvalley.exception.NotFoundException;
import com.prashanth.sunvalley.mapper.LocationMapper;
import com.prashanth.sunvalley.mapper.StudentMapper;
import com.prashanth.sunvalley.repository.LocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final StudentMapper studentMapper;

    public LocationServiceImpl(LocationRepository locationRepository, LocationMapper locationMapper, StudentMapper studentMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
        this.studentMapper = studentMapper;
    }

    @Override
    public List<LocationDTO> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(locationMapper::locationToLocationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LocationDTO getLocationById(Long id) {
        Optional<Location> locationOptional = locationRepository.findById(id);
        if(locationOptional.isPresent())
            return locationMapper.locationToLocationDTO(locationOptional.get());
        else
            throw new NotFoundException("Location with id: " + id + " not found");
    }

    @Override
    @Transactional
    public List<LocationDTO> createLocations(LocationListDTO locationListDTO) {

        return locationListDTO.getLocations().stream()
                .map(this::createLocation)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LocationDTO createLocation(LocationDTO locationDTO) {

        return locationMapper.locationToLocationDTO(locationRepository.save(
                locationMapper.locationDTOToLocation(locationDTO)
        ));
    }

    @Override
    @Transactional
    public LocationDTO updateLocation(Long id, LocationDTO locationDTO) {

        Location location = locationMapper.locationDTOToLocation(locationDTO);
        location.setId(id);
        return locationMapper.locationToLocationDTO(locationRepository.save(location));
    }

    @Override
    @Transactional
    public void deleteLocationById(Long id) {
        locationRepository.deleteById(id);

    }

    @Override
    public List<StudentDTO> getAllStudentsOfLocationById(Long id) {
        Optional<Location> locationOptional = locationRepository.findById(id);
        if(locationOptional.isPresent()) {
            return locationOptional.get().getStudents().stream()
                    .map(student ->
                        studentMapper.studentAndFeeToStudentDTO(student, student.getFee())
                    ).collect(Collectors.toList());
        }else
            throw new NotFoundException("Location with id: " + id + " not found");
    }


}
