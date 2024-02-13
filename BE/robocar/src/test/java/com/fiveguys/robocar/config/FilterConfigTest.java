package com.fiveguys.robocar.config;

import com.fiveguys.robocar.util.JwtUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FilterConfigTest {

    @Test
    void testUtil(){
        JwtUtil jwtUtil = new JwtUtil();
        String loginId = "tester1";
        String token = jwtUtil.createToken(loginId);
        System.out.println(token);
        assertEquals(loginId, jwtUtil.extractLoginId(token));
    }


}
