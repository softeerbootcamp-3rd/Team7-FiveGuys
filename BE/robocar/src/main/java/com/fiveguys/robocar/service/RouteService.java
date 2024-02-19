package com.fiveguys.robocar.service;

import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.apiPayload.exception.GeneralException;
import com.fiveguys.robocar.dto.RouteInfo;
import com.fiveguys.robocar.util.JsonParserUtil;
import com.fiveguys.robocar.util.JsonParserUtil.Coordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {

    private final MapService mapService;

    @Autowired
    public RouteService(MapService mapService) {
        this.mapService = mapService;
    }

    // 경로 정보를 가져오는 메서드
    public RouteInfo getRouteInfo(String start, String goal1, String goal2) {
        ResponseEntity<?> response = mapService.getRoute(start, goal1, goal2);
        if (response.getBody() instanceof String jsonResponse) {
            long duration = JsonParserUtil.extractDuration(jsonResponse);
            long taxiFare = JsonParserUtil.extractTaxiFare(jsonResponse);
            List<Coordinate> pathCoordinates = JsonParserUtil.extractPathCoordinates(jsonResponse);

            return new RouteInfo(duration, taxiFare, pathCoordinates);
        } else {
            throw new GeneralException(ResponseStatus._BAD_REQUEST);
        }
    }
}
