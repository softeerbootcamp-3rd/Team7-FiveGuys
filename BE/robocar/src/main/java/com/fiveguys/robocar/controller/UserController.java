package com.fiveguys.robocar.controller;

import com.fiveguys.robocar.apiPayload.ResponseApi;
import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.dto.req.UserReqDto;
import com.fiveguys.robocar.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "유저")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "회원가입")
    @Parameters(value = {
            @Parameter(name = "loginId", description = "아이디"),
            @Parameter(name = "name", description = "닉네임"),
            @Parameter(name = "password", description = "비밀번호")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 데이터"),
            @ApiResponse(responseCode = "409", description = "아이디/닉네임 중복")
    })
    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody @Validated UserReqDto userReqDto, Errors erros){
        if(erros.hasErrors())
            return ResponseApi.invalidArguments();
        try{
            userService.createUser(userReqDto);
            return ResponseApi.ok();
        } catch(Exception e) {
            return ResponseApi.of(ResponseStatus.TEST_EXCEPTION);
        }
    }



}
