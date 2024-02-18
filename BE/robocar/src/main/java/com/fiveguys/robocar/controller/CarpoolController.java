package com.fiveguys.robocar.controller;


import com.fiveguys.robocar.apiPayload.ResponseApi;
import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.dto.req.CarpoolRegisterReqDto;
import com.fiveguys.robocar.service.CarpoolRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Carpool", description = "카풀리스트 - 등록,조회,삭제")
public class CarpoolController {

    CarpoolRequestService carpoolRequestService;

    @Autowired
    CarpoolController(CarpoolRequestService carpoolRequestService){
        this.carpoolRequestService = carpoolRequestService;
    }

    @Operation(summary = "게스트 위치 기반 리스트 조회")
    @Parameters(value = {
            @Parameter(name = "guestDepartAddress", description = "게스트 출발지"),
            @Parameter(name = "guestDestAddress", description = "게스트 도착지")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "로그인 안됨"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @GetMapping("/operations/carpools")
    public ResponseEntity carpoolListUp(@RequestParam String guestDepartAddress, @RequestParam String guestDestAddress){

        try {
            carpoolRequestService.carpoolListUp(guestDepartAddress, guestDestAddress);
            return ResponseApi.ok();
        } catch (Exception e){
            return ResponseApi.of(ResponseStatus._INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "호스트가 카풀 등록")
    @Parameters(value = {
            @Parameter(name = "latitude", description = "위도"),
            @Parameter(name = "longitude", description = "경도"),


    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추가 성공")
    })
    @PostMapping("/operations")
    public ResponseEntity carpoolRegister(@RequestBody @Validated CarpoolRegisterReqDto carpoolRegisterReqDto){
        return ResponseApi.ok();
    }

    @Operation(summary = "호스트가 카풀 등록")
    @Parameters(value = {
            @Parameter(name = "latitude", description = "위도"),
            @Parameter(name = "longitude", description = "경도"),


    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추가 성공")
    })
    @DeleteMapping("/operations")
    public ResponseEntity carpoolDelete(HttpServletRequest request){
        return ResponseApi.ok();
    }






}
