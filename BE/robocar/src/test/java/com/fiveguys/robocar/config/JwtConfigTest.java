package com.fiveguys.robocar.config;

import com.fiveguys.robocar.util.JwtUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtConfigTest {

    @Test
    void testUtil(){
        JwtUtil jwtUtil = new JwtUtil();
        String loginId = "tester1";
        String token = jwtUtil.createToken(loginId);

        assertEquals(loginId, jwtUtil.extractLoginId(token));
    }


}
