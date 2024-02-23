package com.fiveguys.robocar.controller;

import com.fiveguys.robocar.apiPayload.ResponseApi;
import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.dto.res.RouteResDto;
import com.fiveguys.robocar.service.OperationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fiveguys.robocar.auth.Auth;
import com.fiveguys.robocar.dto.req.CarpoolRegisterReqDto;
import com.fiveguys.robocar.dto.req.CarpoolSuccessReqDto;
import com.fiveguys.robocar.dto.res.CarpoolListUpResDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Tag(name = "Operation", description = "Operation 도메인 관련 API")
public class OperationController {

    private final OperationService operationService;

    @Operation(summary = "최적화된 경로 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")})
    @GetMapping("/operations/optimized-route")
    public ResponseEntity<?> getOptimizedRoute(
            @Parameter(description = "출발지 주소") @RequestParam String departureAddress,
            @Parameter(description = "호스트 목적지 주소") @RequestParam String hostDestAddress,
            @Parameter(description = "게스트 목적지 주소 (선택)") @RequestParam(required = false) String guestDestAddress,
            @Parameter(description = "호스트 ID") @RequestParam Long hostId,
            @Parameter(description = "게스트 ID (선택)") @RequestParam(required = false) Long guestId) {

        try {
            RouteResDto response = operationService.getOptimizedRoute(departureAddress, hostDestAddress, guestDestAddress, hostId, guestId);
            return ResponseApi.ok(response);
        } catch (Exception e) {
            return ResponseApi.of(ResponseStatus.OPERATION_NOT_FOUND);
        }

    }

    @Operation(summary = "게스트 위치 기반 리스트 조회")
    @Parameters(value = {
            @Parameter(name = "guestDepartAddress", description = "게스트 출발지"),
            @Parameter(name = "guestDestAddress", description = "게스트 도착지"),
            @Parameter(name = "maleCount", description = "남성 인원수"),
            @Parameter(name = "femaleCount", description = "여성 인원수"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "로그인 안됨"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @GetMapping("/operations/carpools")
    public ResponseEntity carpoolListUp(@RequestParam String guestDepartAddress, @RequestParam String guestDestAddress,@RequestParam int maleCount,@RequestParam int femaleCount){
        CarpoolListUpResDto carpoolListUpResDto;

        try {
            carpoolListUpResDto = operationService.carpoolListUp(guestDepartAddress, guestDestAddress,maleCount, femaleCount);
            return ResponseApi.ok(carpoolListUpResDto);
        } catch (IllegalArgumentException e) {
            return ResponseApi.of(ResponseStatus.ADDRESS_INPUT_INVALID);
        } catch (Exception e){
            return ResponseApi.of(ResponseStatus._INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "호스트가 카풀 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추가 성공"),
            @ApiResponse(responseCode = "400", description = "추가 실패"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @PostMapping("/operations/carpools")
    public ResponseEntity carpoolRegister(@RequestBody @Validated CarpoolRegisterReqDto carpoolRegisterReqDto, @Auth Long id, Errors errors){
        if(errors.hasErrors())
            return ResponseApi.of(ResponseStatus._INVALID_ARGUMENT);

        try{
            operationService.carpoolRegister(carpoolRegisterReqDto, id);
        } catch (IllegalArgumentException e){
            return ResponseApi.of(ResponseStatus.ADDRESS_INPUT_INVALID);
        } catch (EntityNotFoundException e){
            return ResponseApi.of(ResponseStatus.MEMBER_NOT_FOUND);
        } catch (Exception e){
            return ResponseApi.of(ResponseStatus._INTERNAL_SERVER_ERROR);
        }

        return ResponseApi.ok(null);
    }

    @Operation(summary = "호스트가 요청을 수락")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 요청 성공"),
            @ApiResponse(responseCode = "400", description = "이미 삭제된 동승 정보"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @DeleteMapping("/operations/carpools")
    public ResponseEntity carpoolSuccess(CarpoolSuccessReqDto carpoolSuccessReqDto, @Auth Long id){
        Long inOperationId;
        try {
            inOperationId = operationService.carpoolSuccess(id, carpoolSuccessReqDto);
        } catch(EntityNotFoundException e){
            return ResponseApi.of(ResponseStatus.CARPOOL_NOT_FOUND);
        }
        catch (Exception e ){
            return ResponseApi.of(ResponseStatus._INTERNAL_SERVER_ERROR);
        }
        return ResponseApi.ok(inOperationId);
    }

    @Operation(summary = "호스트가 매칭을 취소")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "삭제 요청 성공"),
            @ApiResponse(responseCode = "400", description = "이미 삭제된 동승 정보"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @DeleteMapping("/operations/carpools/cancel")
    public ResponseEntity carpoolRequestCancel(@Auth Long id){
        try{
            operationService.carpoolRequestCancel(id);
        } catch(Exception e){
            return ResponseApi.of(ResponseStatus._INTERNAL_SERVER_ERROR);
        }
        return ResponseApi.ok(null);
    }

}
