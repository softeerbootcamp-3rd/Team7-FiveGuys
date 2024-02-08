package com.fiveguys.robocar.service;

import com.fiveguys.robocar.apiPayload.GeneralExceptionHandler;
import com.fiveguys.robocar.apiPayload.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    @Override
    public void CheckFlag(Integer flag) {
        if (flag == 1)
            throw new GeneralExceptionHandler(ResponseStatus.TEST_EXCEPTION);
    }

}
