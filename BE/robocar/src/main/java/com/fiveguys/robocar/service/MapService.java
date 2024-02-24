package com.fiveguys.robocar.service;

import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.apiPayload.exception.GeneralException;
import com.fiveguys.robocar.config.NaverMapConfig;
import com.fiveguys.robocar.util.JsonParserUtil;
import com.fiveguys.robocar.util.JsonParserUtil.Coordinate;
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

        String url = String.format("%s?start=%s&goal=%s%s&option=trafast", NaverMapConfig.DIRECTION_API_URL, start, goal1, waypointParam);

        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        } catch (RestClientException e) {
            throw new GeneralException(ResponseStatus.EXTERNAL_SERVICE_ERROR);
        }
    }

    public Coordinate convertAddressToCoordinates(String address) {
        String url = String.format("%s?query=%s", NaverMapConfig.GEOCODE_API_URL,address);
        HttpHeaders headers = createHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return JsonParserUtil.extractCoordinatesFromAddressResponse(response.getBody());
        } catch (RestClientException e) {
            throw new GeneralException(ResponseStatus.EXTERNAL_SERVICE_ERROR);
        }
    }

    // 헤더 설정을 위한 메소드
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", apiKeyId);
        headers.set("X-NCP-APIGW-API-KEY", apiKeySecret);
        return headers;
    }
}
