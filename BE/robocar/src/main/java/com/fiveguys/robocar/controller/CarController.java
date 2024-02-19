package com.fiveguys.robocar.controller;

import com.fiveguys.robocar.apiPayload.ResponseApi;
import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.dto.req.CarReqDto;
import com.fiveguys.robocar.entity.Car;
import com.fiveguys.robocar.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Car", description = "Car 도메인 API")
public class CarController {

    private final CarService carService;

    @Operation(summary = "차량 추가하기")
    @Parameters(value = {
            @Parameter(name = "garageId", description = "차고지 ID"),
            @Parameter(name = "state", description = "운행 상태"),
            @Parameter(name = "seatTemperature", description = "시트 온도"),
            @Parameter(name = "ventilationLevel", description = "통풍 레벨"),
            @Parameter(name = "airConditionerTemperature", description = "에어컨 온도"),
            @Parameter(name = "doorLock", description = "문 잠금"),
            @Parameter(name = "carName", description = "차량 이름"),
            @Parameter(name = "carImage", description = "차량 이미지"),
            @Parameter(name = "carNumber", description = "차량 번호")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/cars")
    public ResponseEntity<?> insertCar(@RequestBody @Validated CarReqDto carReqDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseApi.invalidArguments();
        }

        try {
            carService.insertCar(carReqDto);
        } catch (IllegalArgumentException e) {
            return ResponseApi.of(ResponseStatus.CAR_ALREADY_EXIST);
        }
        return ResponseApi.ok();
    }

    @Operation(summary = "차량 단일 조회")
    @Parameter(name = "id", description = "조회할 차량 ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당하는 차량이 없음")
    })
    @GetMapping("/cars/{id}")
    public ResponseEntity<?> getCar(@PathVariable Long id) {
        Car findCar = null;
        try {
            findCar = carService.getCar(id);
        } catch (EntityNotFoundException e) {
            return ResponseApi.of(ResponseStatus.CAR_NOT_FOUND);
        }
        return ResponseApi.ok(findCar);
    }

    @Operation(summary = "차량 모두 조회")
    @ApiResponse(responseCode = "200", description = "모든 차량 조회 성공")
    @GetMapping("/cars")
    public ResponseEntity getCarAll() {
        List findCarAll = carService.getCarAll();
        return ResponseApi.ok(findCarAll);
    }

    @Operation(summary = "차량 정보 수정")
    @Parameters(value = {
            @Parameter(name = "id", description = "수정할 차량 ID"),
            @Parameter(name = "garageId", description = "차고지 ID"),
            @Parameter(name = "state", description = "운행 상태"),
            @Parameter(name = "seatTemperature", description = "시트 온도"),
            @Parameter(name = "ventilationLevel", description = "통풍 레벨"),
            @Parameter(name = "airConditionerTemperature", description = "에어컨 온도"),
            @Parameter(name = "doorLock", description = "문 잠금"),
            @Parameter(name = "carName", description = "차량 이름"),
            @Parameter(name = "carImage", description = "차량 이미지"),
            @Parameter(name = "carNumber", description = "차량 번호")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "변경 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 또는 유효하지 않은 차량 정보"),
            @ApiResponse(responseCode = "404", description = "해당하는 차량이 없음"),
            @ApiResponse(responseCode = "409", description = "중복된 차량 정보로 수정")
    })
    //Put?Patch?
    @PutMapping("/cars/{id}")
    public ResponseEntity<?> updateCar(@PathVariable Long id, @RequestBody @Valid CarReqDto carReqDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseApi.invalidArguments();
        }

        Car editedCar = null;
        try {
            editedCar = carService.editCar(id, carReqDto);
        } catch (EntityNotFoundException e) {
            return ResponseApi.of(ResponseStatus.CAR_NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseApi.of(ResponseStatus.CAR_ALREADY_EXIST);
        }

        return ResponseApi.ok(editedCar);
    }


    @Operation(summary = "차량 삭제")
    @Parameter(name = "id", description = "삭제할 차량 ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "400", description = "해당하는 차량이 없음")})
    @DeleteMapping("/cars/{id}")
    public ResponseEntity deleteCar(Long id) {
        try {
            carService.deleteCar(id);
        } catch (EntityNotFoundException e) {
            return ResponseApi.of(ResponseStatus.CAR_NOT_FOUND);
        }

        return ResponseApi.ok();
    }
}
