package com.prashanth.sunvalley.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationListDTO {
    private List<LocationDTO> locations;
}
