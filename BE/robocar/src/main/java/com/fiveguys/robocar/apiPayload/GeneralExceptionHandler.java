package com.fiveguys.robocar.apiPayload;

import com.fiveguys.robocar.apiPayload.exception.GeneralException;

public class GeneralExceptionHandler extends GeneralException {

    public GeneralExceptionHandler(ResponseStatus status) {
        super(status);
    }
}
