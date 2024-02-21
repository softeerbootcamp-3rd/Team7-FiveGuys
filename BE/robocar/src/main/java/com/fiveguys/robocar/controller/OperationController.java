package com.fiveguys.robocar.controller;

import com.fiveguys.robocar.apiPayload.ResponseApi;
import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.dto.res.RouteResDto;
import com.fiveguys.robocar.service.OperationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Operation", description = "운행 최적화 도메인 API")
public class OperationController {

    private final OperationService operationService;

    @Operation(summary = "최적화된 경로 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")})
    @GetMapping("/operations/optimized-route")
    public ResponseEntity getOptimizedRoute(
            @Parameter(description = "출발지 주소") @RequestParam String departureAddress,
            @Parameter(description = "호스트 목적지 주소") @RequestParam String hostDestAddress,
            @Parameter(description = "게스트 목적지 주소 (선택)") @RequestParam(required = false) String guestDestAddress) {

        try {
            RouteResDto response = operationService.getOptimizedRoute(departureAddress, hostDestAddress, guestDestAddress);
            return ResponseApi.ok(response);
        } catch (Exception e) {
            return ResponseApi.of(ResponseStatus._BAD_REQUEST);
        }
    }
}
