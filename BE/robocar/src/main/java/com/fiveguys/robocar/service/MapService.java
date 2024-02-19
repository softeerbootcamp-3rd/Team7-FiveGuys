package com.fiveguys.robocar.service;

import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.apiPayload.exception.GeneralException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
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
        String url = buildUrl(start, goal1, goal2);
        HttpHeaders headers = createHeaders();

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        } catch (RestClientException e) {
            throw new GeneralException(ResponseStatus.EXTERNAL_SERVICE_ERROR);
        }
    }

    // URL 생성을 위한 메소드
    private String buildUrl(String start, String goal1, String goal2) {
        String waypointParam = goal2 != null && !goal2.isEmpty() ? "&waypoints=" + goal2 : "";
        return String.format("https://naveropenapi.apigw.ntruss.com/map-direction-15/v1/driving?start=%s&goal=%s%s&option=trafast",
                start.replace(" ", ""), goal1.replace(" ", ""), waypointParam.replace(" ", ""));
    }

    // 헤더 설정을 위한 메소드
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", apiKeyId);
        headers.set("X-NCP-APIGW-API-KEY", apiKeySecret);
        return headers;
    }
}
