package com.prashanth.sunvalley.Controller;

import com.prashanth.sunvalley.Model.LocationDTO;
import com.prashanth.sunvalley.Model.LocationListDTO;
import com.prashanth.sunvalley.Model.StudentDTO;
import com.prashanth.sunvalley.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public LocationListDTO getAllLocations(){
        return new LocationListDTO(locationService.getAllLocations());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LocationDTO getLocationById(@PathVariable Long id){
        return locationService.getLocationById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocationListDTO createLocation(@RequestBody LocationListDTO locationListDTO){
        return new LocationListDTO(locationService.createLocations(locationListDTO));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LocationDTO updateLocationById(@PathVariable Long id, @RequestBody LocationDTO locationDTO){
        return locationService.updateLocation(id,locationDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLocationById(@PathVariable Long id){
        locationService.deleteLocationById(id);
    }

    @GetMapping("/{id}/students")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentDTO> getAllStudentsByLocationId(@PathVariable Long id){
        return locationService.getAllStudentsOfLocationById(id);
    }
}
