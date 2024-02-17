package com.fiveguys.robocar.controller;

import com.fiveguys.robocar.apiPayload.ResponseApi;
import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.dto.req.UserCreateReqDto;
import com.fiveguys.robocar.dto.req.UserLoginReqDto;
import com.fiveguys.robocar.dto.req.UserNicknameReqDto;
import com.fiveguys.robocar.dto.req.UserPasswordReqDto;
import com.fiveguys.robocar.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
            @Parameter(name = "nickname", description = "닉네임"),
            @Parameter(name = "password", description = "비밀번호")
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "409", description = "아이디/닉네임 중복"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody @Validated UserCreateReqDto userCreateReqDto, Errors errors){
        if(errors.hasErrors())
            return ResponseApi.invalidArguments();

        try{
            userService.createUser(userCreateReqDto);
        } catch(DataIntegrityViolationException e){
            return ResponseApi.of(ResponseStatus.USER_CONFLICT);
        } catch(Exception e) {
            return ResponseApi.of(ResponseStatus._INTERNAL_SERVER_ERROR);
        }

        return ResponseApi.of(ResponseStatus.USER_CREATE_OK);
    }

    @Operation(summary = "닉네임 수정")
    @Parameters(value = {
            @Parameter(name = "nickname", description = "닉네임"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 수정 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 유저"),
            @ApiResponse(responseCode = "409", description = "닉네임 중복"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @PatchMapping("/users/nickname")
    public ResponseEntity modifyNickname(@RequestBody @Validated UserNicknameReqDto userNicknameReqDto,Errors errors){
        if(errors.hasErrors())
            return ResponseApi.invalidArguments();

        try{
            userService.modifyNickname(userNicknameReqDto);
        } catch (EntityNotFoundException e){
            return ResponseApi.of(ResponseStatus.USER_NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            return ResponseApi.of(ResponseStatus.USER_CONFLICT);
        } catch(Exception e) {
            return ResponseApi.of(ResponseStatus._INTERNAL_SERVER_ERROR);
        }

        return ResponseApi.ok();
    }

    @Operation(summary = "비밀번호 수정")
    @Parameters(value = {
            @Parameter(name = "password", description = "비밀번호"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀 수정 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 유저"),
            @ApiResponse(responseCode = "401", description = "권한이 없음"),
            @ApiResponse(responseCode = "500", description = "서버 에러")

    })
    @PatchMapping("/users/password")
    public ResponseEntity modifyPassword(@RequestBody @Validated UserPasswordReqDto userPasswordReqDto, Errors errors){
        if(errors.hasErrors())
            return ResponseApi.invalidArguments();

        try{
            userService.modifyPassword(userPasswordReqDto);
        } catch (EntityNotFoundException e){
            return ResponseApi.of(ResponseStatus.USER_NOT_FOUND);
        } catch(Exception e) {
            return ResponseApi.of(ResponseStatus._INTERNAL_SERVER_ERROR);
        }

        return ResponseApi.ok();
    }

    @Operation(summary = "아이디 중복 체크")
    @Parameters(value = {
            @Parameter(name = "loginId", description = "아이디"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용가능 여부"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @GetMapping("/users/loginId-validation")
    public ResponseEntity checkLoginId(@RequestParam String loginId){
        boolean isUsable;

        try{
            isUsable = userService.checkLoginId(loginId);
        } catch(Exception e) {
            return ResponseApi.of(ResponseStatus._INTERNAL_SERVER_ERROR);
        }

        return ResponseApi.ok(isUsable);
    }

    @Operation(summary = "닉네임 중복 체크")
    @Parameters(value = {
            @Parameter(name = "nickname", description = "닉네임"),
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용가능 여부"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @GetMapping("/users/nickname-validation")
    public ResponseEntity checkNickname(@RequestParam String nickname){
        boolean isUsable;

        try{
            isUsable = userService.checkNickname(nickname);
        } catch(Exception e) {
            return ResponseApi.of(ResponseStatus._INTERNAL_SERVER_ERROR);
        }

        return ResponseApi.ok(isUsable);
    }

    @Operation(summary = "회원탈퇴")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "탈퇴성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 유저"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @DeleteMapping("/users")
    public ResponseEntity userResign(@RequestBody Long userId){

        try{
            userService.userResign(userId);
        } catch (EntityNotFoundException e){
            return ResponseApi.of(ResponseStatus.USER_NOT_FOUND);
        }
        catch(Exception e){
            return ResponseApi.of(ResponseStatus._INTERNAL_SERVER_ERROR);
        }
        return ResponseApi.ok();
    }

    @Operation(summary = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "로그인 실패"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @DeleteMapping("/users")
    public ResponseEntity userLogin(@RequestBody UserLoginReqDto userLoginReqDto){

        try{
            userService.userLogin(userLoginReqDto);
        } catch (EntityNotFoundException e){

        }
        catch(Exception e){
            return ResponseApi.of(ResponseStatus._INTERNAL_SERVER_ERROR);
        }
        return ResponseApi.ok(); // 토큰 넣어주기
    }



}
