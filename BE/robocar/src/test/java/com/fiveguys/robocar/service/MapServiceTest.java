package com.fiveguys.robocar.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
public class MapServiceTest {

    @Autowired
    private MapService mapService;

    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper 주입

    @Test
    public void testGetRoute() throws Exception { // 예외 처리 추가
        String start = "127.1058342,37.359708";
        String goal1 = "127.1230000,37.323";
        String goal2 = "127.1156,37.3456";
        ResponseEntity<?> responseEntity = mapService.getRoute(start, goal1, goal2);

        // 응답 본문을 JSON 문자열로 변환하여 출력
        String jsonResponse = objectMapper.writeValueAsString(responseEntity.getBody());
        System.out.println("응답 JSON: " + jsonResponse);
    }
}

