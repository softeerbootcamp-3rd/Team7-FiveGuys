package com.fiveguys.robocar.repository;

import com.fiveguys.robocar.entity.Car;
import com.fiveguys.robocar.models.CarState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByCarNumber(String carNumber);
    Optional<Car> findByGarageIdAndState(Long garageId, CarState state);
}

