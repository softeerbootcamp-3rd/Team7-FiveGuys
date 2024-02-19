package com.fiveguys.robocar.service;

import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.apiPayload.exception.GeneralException;
import com.fiveguys.robocar.config.NaverMapConfig;
import com.fiveguys.robocar.util.JsonParserUtil;
import com.fiveguys.robocar.util.JsonParserUtil.Coordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@Service
public class MapService {
    private final RestTemplate restTemplate;
    private final NaverMapConfig naverMapConfig;

    @Autowired
    public MapService(RestTemplate restTemplate, NaverMapConfig naverMapConfig) {
        this.restTemplate = restTemplate;
        this.naverMapConfig = naverMapConfig;
    }

    public ResponseEntity<String> getRoute(String start, String goal1, String goal2) {
        String waypointParam = goal2 != null && !goal2.isEmpty() ? "&waypoints=" + goal2 : "";
        String url = String.format("%s?start=%s&goal=%s%s&option=trafast",
                naverMapConfig.DIRECTION_API_URL, UriUtils.encodeQueryParam(start, StandardCharsets.UTF_8), UriUtils.encodeQueryParam(goal1, StandardCharsets.UTF_8), UriUtils.encodeQueryParam(waypointParam, StandardCharsets.UTF_8));

        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        } catch (RestClientException e) {
            throw new GeneralException(ResponseStatus.EXTERNAL_SERVICE_ERROR);
        }
    }

    public Coordinate convertAddressToCoordinates(String address) {
        String url = String.format("%s?query=%s", naverMapConfig.GEOCODE_API_URL,address);

        HttpHeaders headers = createHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return JsonParserUtil.extractCoordinatesFromAddressResponse(response.getBody());
        } catch (RestClientException e) {
            throw new GeneralException(ResponseStatus.EXTERNAL_SERVICE_ERROR);
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-NCP-APIGW-API-KEY-ID", naverMapConfig.getApiKeyId());
        headers.set("X-NCP-APIGW-API-KEY", naverMapConfig.getApiKeySecret());
        return headers;
    }
}
