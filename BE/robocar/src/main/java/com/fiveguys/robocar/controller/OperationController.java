package com.fiveguys.robocar.controller;

import com.fiveguys.robocar.apiPayload.ResponseApi;
import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.auth.Auth;
import com.fiveguys.robocar.dto.req.CarpoolRegisterReqDto;
import com.fiveguys.robocar.dto.req.CarpoolSuccessReqDto;
import com.fiveguys.robocar.dto.res.CarpoolListUpResDto;
import com.fiveguys.robocar.service.OperationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Operation", description = "Operation 도메인 관련 API")
public class OperationController {

    OperationService operationService;

    @Autowired
    OperationController(OperationService operationService){
        this.operationService = operationService;
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

        try {
            operationService.carpoolSuccess(id, carpoolSuccessReqDto);
        } catch(EntityNotFoundException e){
            return ResponseApi.of(ResponseStatus.CARPOOL_NOT_FOUND);
        } catch (JSONException e){
          return ResponseApi.of(ResponseStatus.MEMBER_NOT_FOUND);
        } catch (Exception e ){
            return ResponseApi.of(ResponseStatus._INTERNAL_SERVER_ERROR);
        }
        return ResponseApi.ok(null);
    }




}
