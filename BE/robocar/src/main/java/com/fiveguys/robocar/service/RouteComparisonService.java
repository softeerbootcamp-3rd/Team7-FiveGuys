package com.fiveguys.robocar.service;

import com.fiveguys.robocar.util.JsonParserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RouteComparisonService {

    @Autowired
    private MapService mapService;

    // 최적의 경로 결정 및 해당 경로의 goal과 waypoint 반환
    public OptimalRoute determineOptimalRoute(String start, String hostDestCoordinate, String guestDestCoordinate) {
        long guestFirstDuration = getRouteDuration(start, hostDestCoordinate, guestDestCoordinate);
        long hostFirstDuration = getRouteDuration(start, guestDestCoordinate, hostDestCoordinate);

        if (hostFirstDuration <= guestFirstDuration) {
            return new OptimalRoute(hostDestCoordinate, guestDestCoordinate);
        } else {
            return new OptimalRoute(guestDestCoordinate, hostDestCoordinate);
        }
    }

    private long getRouteDuration(String start, String goal, String waypoint) {
        ResponseEntity<String> response = mapService.getRoute(start, goal, waypoint);
        if (response.getBody() != null) {
            return JsonParserUtil.extractDuration(response.getBody());
        }
        return Long.MAX_VALUE; // 에러 발생 시, 최대값 반환
    }

    // 최적 경로 정보를 담는 클래스
    public static class OptimalRoute {
        private final String firstDestination;
        private final String secondDestination;

        public OptimalRoute(String firstDestination, String secondDestination) {
            this.firstDestination = firstDestination;
            this.secondDestination = secondDestination;
        }

        // Getters
        public String getFirstDestination() {
            return firstDestination;
        }

        public String getSecondDestination() {
            return secondDestination;
        }
    }
}
