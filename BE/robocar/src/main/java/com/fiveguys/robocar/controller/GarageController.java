package com.fiveguys.robocar.controller;

import com.fiveguys.robocar.apiPayload.ResponseApi;
import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.dto.req.GarageReqDto;
import com.fiveguys.robocar.entity.Garage;
import com.fiveguys.robocar.service.GarageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Tag(name = "Garage", description = "Garage(차고) 도메인 API")
public class GarageController {

    private final GarageService garageService;

    @Operation(summary = "차고지 추가하기")
    @Parameters(value = {
            @Parameter(name = "latitude", description = "위도"),
            @Parameter(name = "longitude", description = "경도")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추가 성공"),
            @ApiResponse(responseCode = "409", description = "중복된 차고지 위치로 추가")})
    @PostMapping("/garage")
    public ResponseEntity insertGarage(@RequestBody @Validated GarageReqDto garageReqDto,
                                       Errors errors) {
        if (errors.hasErrors()) {
            return ResponseApi.invalidArguments();
        }

        try {
            garageService.insertGarage(garageReqDto);
        } catch (IllegalArgumentException e) {
            return ResponseApi.of(ResponseStatus.GARAGE_ALREADY_EXIST);
        }
        return ResponseApi.ok();
    }

    @Operation(summary = "차고지 단일 조회")
    @Parameter(name = "id", description = "조회할 차고지 id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추가 성공"),
            @ApiResponse(responseCode = "400", description = "해당하는 차고지가 없음")})
    @GetMapping("/garage/{id}")
    public ResponseEntity getGarage(Long id) {
        Garage findGarage = null;
        try {
            findGarage = garageService.getGarage(id);
        } catch (EntityNotFoundException e) {
            return ResponseApi.of(ResponseStatus.GARAGE_NOT_FOUND);
        }

        return ResponseApi.ok(findGarage);
    }

    @Operation(summary = "차고지 모두 조회")
    @ApiResponse(responseCode = "200", description = "추가 성공")
    @GetMapping("/garages")
    public ResponseEntity getGarageAll() {
        List findGarageAll = garageService.getGarageAll();
        return ResponseApi.ok(findGarageAll);
    }

    @Operation(summary = "차고지 위치 수정")
    @Parameters(value = {
            @Parameter(name = "id", description = "변경할 차고지 id"),
            @Parameter(name = "latitude", description = "수정할 위도"),
            @Parameter(name = "longitude", description = "수정할 경도")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "변경 성공"),
            @ApiResponse(responseCode = "400", description = "해당하는 차고지가 없음"),
            @ApiResponse(responseCode = "409", description = "중복된 차고지 위치로 수정")})
    @PutMapping("/garage/{id}")
    public ResponseEntity editGarage(Long id,
                                     @RequestBody @Validated GarageReqDto garageReqDto,
                                     Errors errors) {
        if (errors.hasErrors()) {
            return ResponseApi.invalidArguments();
        }

        Garage editedGarage = null;
        try {
            editedGarage = garageService.editGarage(id, garageReqDto);
        } catch (EntityNotFoundException e) {
            return ResponseApi.of(ResponseStatus.GARAGE_NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseApi.of(ResponseStatus.GARAGE_ALREADY_EXIST);
        }

        return ResponseApi.ok(editedGarage);
    }

    @Operation(summary = "차고지 삭제")
    @Parameter(name = "id", description = "삭제할 차고지 id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "변경 성공"),
            @ApiResponse(responseCode = "400", description = "해당하는 차고지가 없음")})
    @DeleteMapping("/garage/{id}")
    public ResponseEntity deleteGarage(Long id) {
        try {
            garageService.deleteGarage(id);
        } catch (EntityNotFoundException e) {
            return ResponseApi.of(ResponseStatus.GARAGE_NOT_FOUND);
        }

        return ResponseApi.ok();
    }
}
