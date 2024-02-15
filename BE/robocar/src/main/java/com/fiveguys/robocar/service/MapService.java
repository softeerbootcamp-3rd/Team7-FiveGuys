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
        String waypointParam = goal2 != null && !goal2.isEmpty() ? "&waypoints=" + goal2 : "";
        String url = String.format("https://naveropenapi.apigw.ntruss.com/map-direction-15/v1/driving?start=%s&goal=%s%s&option=trafast", start.replace(" ", ""), goal1.replace(" ", ""), waypointParam.replace(" ", ""));

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", apiKeyId);
        headers.set("X-NCP-APIGW-API-KEY", apiKeySecret);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        } catch (RestClientException e) {
            // 새로운 ResponseStatus를 사용하여 GeneralException을 던집니다.
            throw new GeneralException(ResponseStatus.EXTERNAL_SERVICE_ERROR);
        }
    }
}
