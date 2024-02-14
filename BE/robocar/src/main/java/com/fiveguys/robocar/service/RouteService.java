package com.fiveguys.robocar.service;

import com.fiveguys.robocar.util.JsonParserUtil;
import com.fiveguys.robocar.util.JsonParserUtil.Coordinate;
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
        if (response.getBody() instanceof String) {
            String jsonResponse = (String) response.getBody();

            long duration = JsonParserUtil.extractDuration(jsonResponse);
            long taxiFare = JsonParserUtil.extractTaxiFare(jsonResponse);
            List<Coordinate> pathCoordinates = JsonParserUtil.extractPathCoordinates(jsonResponse);

            // RouteInfo는 경로에 대한 정보를 담는 클래스
            return new RouteInfo(duration, taxiFare, pathCoordinates);
        } else {
            return null; // 일단 예외처리
        }
    }

    // 경로 정보를 담는 클래스
    public static class RouteInfo {
        private long duration;
        private long taxiFare;
        private List<Coordinate> pathCoordinates;

        public RouteInfo(long duration, long taxiFare, List<Coordinate> pathCoordinates) {
            this.duration = duration;
            this.taxiFare = taxiFare;
            this.pathCoordinates = pathCoordinates;
        }

        // Getters
        public long getDuration() {
            return duration;
        }

        public long getTaxiFare() {
            return taxiFare;
        }

        public List<Coordinate> getPathCoordinates() {
            return pathCoordinates;
        }
    }
}
