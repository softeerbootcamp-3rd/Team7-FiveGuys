package com.fiveguys.robocar.service;

import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.apiPayload.exception.GeneralException;
import com.fiveguys.robocar.util.JsonParserUtil;
import com.fiveguys.robocar.util.JsonParserUtil.Coordinate;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RouteService {

    @Autowired
    private MapService mapService;

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

    // 경로 정보를 담는 클래스
    @Getter // Lombok 어노테이션을 사용하여 모든 필드의 getter 메서드를 자동 생성
    public static class RouteInfo {
        private final long duration;
        private final long taxiFare;
        private final List<Coordinate> pathCoordinates;

        public RouteInfo(long duration, long taxiFare, List<Coordinate> pathCoordinates) {
            this.duration = duration;
            this.taxiFare = taxiFare;
            this.pathCoordinates = pathCoordinates;
        }
    }
}
