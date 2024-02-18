package com.fiveguys.robocar.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
class JwtUtilTest {

    JwtUtil jwtUtil;
    @Autowired
    JwtUtilTest(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Test
    @DisplayName("토큰이 잘 생성되는지 확인")
    void testUtil(){
        Long userId = 12312L;
        String token = jwtUtil.createToken(userId);
        log.info("token: [{}]",token);
        log.info("userId:[{}]",jwtUtil.extractUserId(token));
        assertEquals(userId, jwtUtil.extractUserId(token));;
    }
}
