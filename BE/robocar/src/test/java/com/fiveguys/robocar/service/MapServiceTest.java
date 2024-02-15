package com.fiveguys.robocar.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiveguys.robocar.util.JsonParserUtil.Coordinate;


@SpringBootTest
public class MapServiceTest {

    @Autowired
    private MapService mapService;

    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper 주입

    @Test
    public void testGetRoute() throws Exception {
        String start = "127.1058342,37.359708";
        String goal1 = "127.1230000,37.323";
        //String goal2 = "127.1156,37.3456";
        String goal2 = null;
        ResponseEntity<?> responseEntity = mapService.getRoute(start, goal1, goal2);

        // 응답 본문을 JSON 문자열로 변환하여 출력
        String jsonResponse = objectMapper.writeValueAsString(responseEntity.getBody());
        System.out.println("응답 JSON: " + jsonResponse);
    }

    @Test
    public void testConvertAddressToCoordinates() {
        // 주소 예시: "분당구 불정로 6"
        String address = "분당구 불정로 6";
        Coordinate coordinate = mapService.convertAddressToCoordinates(address);

        // 좌표 출력
        System.out.println("Converted Coordinates:");
        System.out.println("Latitude: " + coordinate.getLatitude());
        System.out.println("Longitude: " + coordinate.getLongitude());
    }
}
