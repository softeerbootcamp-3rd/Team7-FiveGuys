package com.fiveguys.robocar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MapService {
    private final RestTemplate restTemplate;
    private final String apiKeyId;
    private final String apiKeySecret;

    @Autowired
    public MapService(RestTemplate restTemplate, @Value("${naver.api.key.id}") String apiKeyId, @Value("${naver.api.key.secret}") String apiKeySecret) {
        this.restTemplate = restTemplate;
        this.apiKeyId = apiKeyId;
        this.apiKeySecret = apiKeySecret;
    }

    public ResponseEntity<String> getRoute(String start, String goal1, String goal2) {
        // waypoint가 있는지 확인하고, 없으면 waypoint 파라미터를 URL에 포함시키지 않음
        String waypointParam = goal2 != null && !goal2.isEmpty() ? "&waypoints=" + goal2.replace(" ", "") : "";
        String url = String.format("https://naveropenapi.apigw.ntruss.com/map-direction-15/v1/driving?start=%s&goal=%s%s&option=trafast", start, goal1, waypointParam);

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", apiKeyId);
        headers.set("X-NCP-APIGW-API-KEY", apiKeySecret);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return response; // API로부터 받은 원본 JSON 응답을 그대로 반환
        } catch (Exception e) {
            System.out.println("API 호출 중 오류 발생: " + e.getMessage());
            return new ResponseEntity<>("API 호출 실패: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
