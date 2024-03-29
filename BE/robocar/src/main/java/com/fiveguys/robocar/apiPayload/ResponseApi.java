package com.fiveguys.robocar.apiPayload;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

public class ResponseApi {
    @Getter
    @Builder
    private static class Body<T> {
        private String message;
        private T data;
    }

    // body가 없을 경우
    public static ResponseEntity of(ResponseStatus status) {
        Body body = Body.builder()
                .message(status.getMessage())
                .build();
        return new ResponseEntity<>(body, status.getHttpStatus());
    }

    public static <T> ResponseEntity<T> of(ResponseStatus status, T data) {
        Body body = Body.builder()
                .message(status.getMessage())
                .data(data)
                .build();
        return new ResponseEntity(body, status.getHttpStatus());
    }

    // 200
    public static ResponseEntity ok() {
        return of(ResponseStatus._OK);
    }

    // 200
    public static <T> ResponseEntity<T> ok(T data) {
        return of(ResponseStatus._OK, data);
    }

    // 400
    public static <T> ResponseEntity<T> badRequest(T data) {
        return of(ResponseStatus._BAD_REQUEST, data);
    }
    public static ResponseEntity invalidArguments() {
        return of(ResponseStatus._INVALID_ARGUMENT);
    }


    // 401
    public static <T> ResponseEntity<T> unauthorized(T data) {
        return of(ResponseStatus._UNAUTHORIZED, data);
    }

    // 403
    public static <T> ResponseEntity<T> forbidden(T data) {
        return of(ResponseStatus._FORBIDDEN, data);
    }

    // 409
    public static <T> ResponseEntity<T> conflict(T data) {
        return of(ResponseStatus._CONFLICT, data);
    }


    // 500
    public static <T> ResponseEntity<T> serverError(T data) {
        return of(ResponseStatus._INTERNAL_SERVER_ERROR, data);
    }

}
