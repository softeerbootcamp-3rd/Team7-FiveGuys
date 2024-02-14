package com.fiveguys.robocar.config;

import com.fiveguys.robocar.util.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FilterConfigTest {
    Logger logger = LoggerFactory.getLogger("JWT Test");
    @Test
    @DisplayName("토큰이 잘 생성되는지 확인")
    void testUtil(){
        String loginId = "tester1";
        String token = JwtUtil.createToken(loginId);
        logger.info("token: [{}]",token);
        assertEquals(loginId, JwtUtil.extractLoginId(token));;
    }


}
