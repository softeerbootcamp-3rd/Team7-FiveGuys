package com.fiveguys.robocar.apiPayload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {
    _OK(org.springframework.http.HttpStatus.OK, "요청에 성공했습니다."),
    // 가장 일반적인 응답
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "금지된 요청입니다."),
    _CONFLICT(HttpStatus.CONFLICT, "중복된 리소스입니다."),
    _INTERNAL_SERVER_ERROR(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러, 관리자에게 문의 바랍니다."),

    // 멤버 관려 에러 예시
    MEMBER_NOT_FOUND(org.springframework.http.HttpStatus.BAD_REQUEST, "사용자가 없습니다."),
    NICKNAME_NOT_EXIST(org.springframework.http.HttpStatus.BAD_REQUEST, "닉네임은 필수 입니다."),

    // 테스트용
    TEST_EXCEPTION(org.springframework.http.HttpStatus.BAD_REQUEST, "Error 테스트"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

}
