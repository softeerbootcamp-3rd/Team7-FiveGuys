package com.fiveguys.robocar.repository;

import com.fiveguys.robocar.entity.Garage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GarageRepository extends JpaRepository<Garage, Long> {
}
