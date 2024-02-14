package com.fiveguys.robocar.repository;

import com.fiveguys.robocar.entity.Garage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GarageRepository extends JpaRepository<Garage, Long> {

    Optional<Garage> findByLatitudeAndLongitude(Double latitude, Double longitude);
}
