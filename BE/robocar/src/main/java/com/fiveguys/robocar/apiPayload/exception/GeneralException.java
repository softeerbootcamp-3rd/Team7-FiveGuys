package com.fiveguys.robocar.apiPayload.exception;

import com.fiveguys.robocar.apiPayload.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private ResponseStatus status;

    public ResponseStatus getResponseStatus() {
        return this.status;
    }

}
