package com.fiveguys.robocar.repository;

import com.fiveguys.robocar.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car,Long> {
    boolean existsByCarNameAndCarNumber(String carName, String carNumber);
}

