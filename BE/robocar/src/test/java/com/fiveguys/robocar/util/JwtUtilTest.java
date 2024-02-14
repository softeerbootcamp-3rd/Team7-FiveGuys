package com.fiveguys.robocar.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
class JwtUtilTest {

    @Test
    @DisplayName("토큰이 잘 생성되는지 확인")
    void testUtil(){
        String loginId = "tester1";
        String token = JwtUtil.createToken(loginId);
        log.info("token: [{}]",token);
        log.info("loginId:[{}]",JwtUtil.extractLoginId(token));
        assertEquals(loginId, JwtUtil.extractLoginId(token));;
    }
}
