package com.fiveguys.robocar.service;

import com.fiveguys.robocar.util.JsonParserUtil.Coordinate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
public class MapServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(MapServiceTest.class);

    @Autowired
    private MapService mapService;

    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper 주입

    @Test
    public void testGetRoute() throws Exception { // 예외 처리 추가
        String start = "127.1058342,37.359708";
        String goal1 = "127.1230000,37.323";
        String goal2 = null;
        ResponseEntity<?> responseEntity = mapService.getRoute(start, goal1, goal2);

        // 응답 본문을 JSON 문자열로 변환하여 로그 출력
        String jsonResponse = objectMapper.writeValueAsString(responseEntity.getBody());
        logger.info("응답 JSON: {}", jsonResponse);
    }

    @Test
    public void testConvertAddressToCoordinates() {
        // 주소 예시: "분당구 불정로 6"
        String address = "서울 광진구 천호대로 584 능동주유소";
        Coordinate coordinate = mapService.convertAddressToCoordinates(address);

        // 좌표 출력
        logger.info("Converted Coordinates:");
        logger.info("Latitude: {}", coordinate.getLatitude());
        logger.info("Longitude: {}", coordinate.getLongitude());
    }
}
