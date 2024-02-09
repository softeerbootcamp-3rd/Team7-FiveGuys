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
        return new ResponseEntity(status.getMessage(), status.getHttpStatus());
    }

    public static <T> ResponseEntity<T> of(ResponseStatus status, T data) {
        Body body = Body.builder()
                .message(status.getMessage())
                .data(data)
                .build();
        return new ResponseEntity(body, status.getHttpStatus());
    }

    // 200
    public static <T> ResponseEntity<T> ok(T data) {
        return of(ResponseStatus._OK, data);
    }

    public static <T> ResponseEntity<T> badRequest(T data) {
        return of(ResponseStatus._BAD_REQUEST, data);
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
