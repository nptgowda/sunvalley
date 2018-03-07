package com.prashanth.sunvalley.repository;

import com.prashanth.sunvalley.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location,Long> {

    Optional<Location> findByLocation(String location);
}
