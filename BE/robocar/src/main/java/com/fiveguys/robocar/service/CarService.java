package com.fiveguys.robocar.service;

import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.dto.req.CarReqDto;
import com.fiveguys.robocar.entity.Car;
import com.fiveguys.robocar.entity.Garage;
import com.fiveguys.robocar.models.CarState;
import com.fiveguys.robocar.repository.CarRepository;
import com.fiveguys.robocar.repository.GarageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final GarageRepository garageRepository;

    @Transactional
    public void insertCar(CarReqDto carReqDto) {
        // 차량 번호로 이미 등록된 차량이 있는지 확인
        Car findCar = carRepository.findByCarNumber(carReqDto.getCarNumber())
                .orElse(null);

        if (findCar != null) {
            throw new IllegalArgumentException(ResponseStatus.CAR_ALREADY_EXIST.getMessage());
        }

// garageId를 사용하여 Garage 엔티티 검색
        Garage garage = garageRepository.findById(carReqDto.getGarageId())
                .orElseThrow(() -> new IllegalArgumentException("Garage not found with id: " + carReqDto.getGarageId()));

        // Car 객체 생성 및 DTO에서 받은 값 설정
        Car car = new Car();
        car.setGarage(garage); // Garage 엔티티 설정
        car.setState(carReqDto.getState());
        car.setSeatTemperature(carReqDto.getSeatTemperature());
        car.setVentilationLevel(carReqDto.getVentilationLevel());
        car.setAirConditionerTemperature(carReqDto.getAirConditionerTemperature());
        car.setDoorLock(carReqDto.isDoorLock());
        car.setCarName(carReqDto.getCarName());
        car.setCarImage(carReqDto.getCarImage());
        car.setCarNumber(carReqDto.getCarNumber());

        // 차량 정보 저장
        carRepository.save(car);
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

        // 차량 번호로 이미 등록된 차량이 있는지 확인
        Optional<Car> existingCar = carRepository.findByCarNumber(updateCarNumber);
        if (existingCar.isPresent() && !existingCar.get().getId().equals(findCar.getId())) {
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
                .orElseThrow(() -> new EntityNotFoundException(ResponseStatus.CAR_NOT_FOUND.getMessage()));
        carRepository.delete(car); // 차량 정보 데이터베이스에서 삭제
    }

    // 가장 가까운 차고에서 사용 가능한 차량을 조회하는 메서드
    public Car findAvailableCar(Long garageId) {
        // 특정 차고에 위치하며 상태가 READY인 차량을 조회
        return carRepository.findByGarageIdAndState(garageId, CarState.READY)
                .orElseThrow(() -> new EntityNotFoundException(ResponseStatus.CAR_NOT_FOUND.getMessage()));
    }
}
