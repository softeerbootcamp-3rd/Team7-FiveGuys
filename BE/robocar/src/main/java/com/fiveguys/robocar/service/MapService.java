package com.fiveguys.robocar.service;

import com.fiveguys.robocar.util.JsonParserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fiveguys.robocar.apiPayload.ResponseApi;

import org.springframework.http.ResponseEntity;

@Service
public class MapService {

    private final RestTemplate restTemplate;
    private final String apiKeyId;
    private final String apiKeySecret;

    @Autowired
    public MapService(RestTemplate restTemplate,
                      @Value("${naver.api.key.id}") String apiKeyId,
                      @Value("${naver.api.key.secret}") String apiKeySecret) {
        this.restTemplate = restTemplate;
        this.apiKeyId = apiKeyId;
        this.apiKeySecret = apiKeySecret;
    }

    public ResponseEntity<?> getRoute(String start, String goal1, String goal2) {
        String url = String.format(
                "https://naveropenapi.apigw.ntruss.com/map-direction-15/v1/driving?start=%s&goal=%s&waypoints=%s&option=trafast",
                start, goal1, goal2.replace(" ", ""));

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", apiKeyId);
        headers.set("X-NCP-APIGW-API-KEY", apiKeySecret);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            long duration = JsonParserUtil.extractDuration(response.getBody());

            System.out.println("Extracted duration: " + duration);

            return ResponseApi.ok(response.getBody());
        } catch (Exception e) {
            System.out.println("API 호출 중 오류 발생: " + e.getMessage());
            return ResponseApi.serverError("API 호출 실패: " + e.getMessage());
        }
    }
}
