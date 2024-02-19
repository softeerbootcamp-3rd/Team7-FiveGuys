package com.fiveguys.robocar.apiPayload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ResponseStatus {
    _OK(OK, "요청에 성공했습니다."),
    // 가장 일반적인 응답
    _BAD_REQUEST(BAD_REQUEST, "잘못된 요청입니다."),
    _INVALID_ARGUMENT(BAD_REQUEST, "입력된 값이 잘못된 형식입니다."),
    _UNAUTHORIZED(UNAUTHORIZED, "인증이 필요합니다."),
    _FORBIDDEN(FORBIDDEN, "금지된 요청입니다."),
    _CONFLICT(CONFLICT, "중복된 리소스입니다."),
    _INTERNAL_SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버 에러, 관리자에게 문의 바랍니다."),

    // 멤버 관련
    MEMBER_NOT_FOUND(BAD_REQUEST, "사용자가 없습니다."),
    NICKNAME_NOT_EXIST(BAD_REQUEST, "닉네임은 필수 입니다."),

    // 차고지 관련
    GARAGE_ALREADY_EXIST(CONFLICT, "중복된 차고지 위치입니다."),
    GARAGE_NOT_FOUND(BAD_REQUEST, "해당하는 차고지가 없습니다."),


    // 차량 관련
    CAR_ALREADY_EXIST(CONFLICT, "이미 등록된 차량입니다."),
    CAR_NOT_FOUND(BAD_REQUEST, "해당하는 차고지가 없습니다."),
    // 결제 관련
    ORDER_NOT_FOUND(BAD_REQUEST, "해당하는 주문이 없습니다."),
    INVALID_PAYMENT_AMOUNT(BAD_REQUEST, "주문 내역의 금액과 요청한 금액이 서로 다릅니다."),
    UNPROCESSABLE_PAYMENT(UNPROCESSABLE_ENTITY, "이미 승인되었거나 만료된 결제 요청입니다."),
    NOT_CANCELABLE_AMOUNT(UNPROCESSABLE_ENTITY, "취소할 수 없는 금액입니다"),

    // 외부 api호출
    EXTERNAL_SERVICE_ERROR(INTERNAL_SERVER_ERROR, "외부 서비스 호출 중 오류가 발생했습니다."),

    // 테스트용
    TEST_EXCEPTION(BAD_REQUEST, "Error 테스트");

    private final HttpStatus httpStatus;
    private final String message;

}
