package com.fiveguys.robocar.controller;

import com.fiveguys.robocar.apiPayload.ResponseApi;
import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.controller.annotation.Auth;
import com.fiveguys.robocar.dto.req.UserCreateReqDto;
import com.fiveguys.robocar.dto.req.UserLoginReqDto;
import com.fiveguys.robocar.dto.req.UserNicknameReqDto;
import com.fiveguys.robocar.dto.req.UserPasswordReqDto;
import com.fiveguys.robocar.dto.res.LoginResDto;
import com.fiveguys.robocar.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "유저", description = "유저 입니다")
public class UserController {

    private final UserService userService;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "409", description = "아이디/닉네임 중복"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody @Validated UserCreateReqDto userCreateReqDto, Errors errors){
        System.out.println("]]"+userCreateReqDto.getLoginId());
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
    public ResponseEntity modifyNickname(@Auth Long id, @RequestBody @Validated UserNicknameReqDto userNicknameReqDto, Errors errors){
        if(errors.hasErrors())
            return ResponseApi.invalidArguments();

        try{
            userService.modifyNickname(userNicknameReqDto, id);
        } catch (EntityNotFoundException e){
            return ResponseApi.of(ResponseStatus.MEMBER_NOT_FOUND);
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
    public ResponseEntity modifyPassword(@Auth Long id, @RequestBody @Validated UserPasswordReqDto userPasswordReqDto, Errors errors){
        if(errors.hasErrors())
            return ResponseApi.invalidArguments();

        try{
            userService.modifyPassword(userPasswordReqDto, id);
        } catch (EntityNotFoundException e){
            return ResponseApi.of(ResponseStatus.MEMBER_NOT_FOUND);
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
        boolean isUnusable;

        try{
            isUnusable = userService.checkLoginId(loginId);
        } catch(Exception e) {
            return ResponseApi.of(ResponseStatus._INTERNAL_SERVER_ERROR);
        }

        return ResponseApi.ok(isUnusable);
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
        boolean isUnusable;

        try{
            isUnusable = userService.checkNickname(nickname);
        } catch(Exception e) {
            return ResponseApi.of(ResponseStatus._INTERNAL_SERVER_ERROR);
        }

        return ResponseApi.ok(isUnusable);
    }

    @Operation(summary = "회원탈퇴")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "탈퇴성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 유저"),
            @ApiResponse(responseCode = "500", description = "서버 에러")
    })
    @DeleteMapping("/users/delete")////
    public ResponseEntity userResign(@Auth Long id){

        try{
            userService.userResign(id);
        } catch (EntityNotFoundException e){
            return ResponseApi.of(ResponseStatus.MEMBER_NOT_FOUND);
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
    @PostMapping("/users/login")
    public ResponseEntity userLogin(@RequestBody UserLoginReqDto userLoginReqDto, Errors errors){

        System.out.println("[[" + userLoginReqDto.getLoginId());
        LoginResDto loginResDto = null;
        try{
            loginResDto = userService.userLogin(userLoginReqDto);
        } catch (EntityNotFoundException e){
            return ResponseApi.of(ResponseStatus.USER_WRONG_LOGIN_INFO);
        } catch(Exception e){
            return ResponseApi.of(ResponseStatus._INTERNAL_SERVER_ERROR);
        }
        return ResponseApi.ok(loginResDto);
    }

}
