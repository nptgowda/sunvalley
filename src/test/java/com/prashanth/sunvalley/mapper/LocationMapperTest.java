package com.prashanth.sunvalley.mapper;

import com.prashanth.sunvalley.Model.LocationDTO;
import com.prashanth.sunvalley.domain.Location;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class LocationMapperTest {

    LocationMapper locationMapper;
    @Before
    public void setUp() throws Exception {
        locationMapper = LocationMapper.INSTANCE;
    }

    @Test
    public void locationToLocationDTO() throws Exception {
        Location location = new Location();
        location.setId(1L);
        location.setLocation("Gowda");
        location.setTransportFee(new BigDecimal(5000));

        LocationDTO locationDTO = locationMapper.locationToLocationDTO(location);

        assertNotNull(locationDTO);
        assertThat(locationDTO.getId(),is(equalTo(location.getId())));
        assertThat(locationDTO.getLocation(),is(equalTo(location.getLocation())));
        assertThat(locationDTO.getTransportFee(),is(equalTo(location.getTransportFee())));

    }
}