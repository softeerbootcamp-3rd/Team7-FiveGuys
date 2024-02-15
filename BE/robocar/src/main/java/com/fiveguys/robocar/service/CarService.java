package com.fiveguys.robocar.service;

import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.dto.req.CarReqDto;
import com.fiveguys.robocar.entity.Car;
import com.fiveguys.robocar.models.CarState;
import com.fiveguys.robocar.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    @Transactional
    public Car insertCar(CarReqDto carReqDto) {
        Car car = new Car(); // 차량 객체 생성 및 carReqDto 데이터 설정 로직 필요
        // 예: car.setCarName(carReqDto.getCarName());
        return carRepository.save(car); // 차량 정보 데이터베이스에 저장
    }

    public Car getCar(Long carId) {
        return carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + carId));
    }

    public List<Car> getCarAll() {
        return carRepository.findAll();
    }

    @Transactional
    public Car editCar(Long id, CarReqDto carReqDto) {
        Car findCar = carRepository.findById(id).orElse(null);

        if (findCar == null) {
            throw new EntityNotFoundException(ResponseStatus.CAR_NOT_FOUND.getMessage());
        }
        CarState updateState = carReqDto.getState();
        Integer updateSeatTemperature = carReqDto.getSeatTemperature();
        Integer updateVentilationLevel = carReqDto.getVentilationLevel();
        Integer updateAirConditionerTemperature = carReqDto.getAirConditionerTemperature();
        boolean updateDoorLock = carReqDto.isDoorLock();
        String updateCarName = carReqDto.getCarName();
        String updateCarImage = carReqDto.getCarImage();
        String updateCarNumber = carReqDto.getCarNumber();

        //carName과 carNumber를 사용하여 동일한 차량이 존재하는지 확인
        boolean carExists = carRepository.existsByCarNameAndCarNumber(updateCarName, updateCarNumber);
        if (carExists && !findCar.getCarNumber().equals(updateCarNumber)) {
            throw new IllegalArgumentException(ResponseStatus.CAR_ALREADY_EXIST.getMessage());
        }

        findCar.edit(updateState, updateSeatTemperature, updateVentilationLevel,
                updateAirConditionerTemperature, updateDoorLock,
                updateCarName, updateCarImage, updateCarNumber);

        return findCar;
    }


    @Transactional
    public void deleteCar(Long carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + carId));
        carRepository.delete(car); // 차량 정보 데이터베이스에서 삭제
    }
}
