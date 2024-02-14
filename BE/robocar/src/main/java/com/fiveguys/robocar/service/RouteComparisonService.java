package com.fiveguys.robocar.service;

import com.fiveguys.robocar.util.JsonParserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RouteComparisonService {

    @Autowired
    private MapService mapService;

    public String compareRoutes(String start, String goal1, String goal2) {
        // start -> goal1 -> goal2 순서로 경로 탐색
        long duration1 = getRouteDuration(start, goal1, goal2);
        // start -> goal2 -> goal1 순서로 경로 탐색
        long duration2 = getRouteDuration(start, goal2, goal1);

        // duration 비교 및 결과 반환
        if (duration1 <= duration2) {
            return String.format("Faster route is Start -> Goal1 -> Goal2 with duration: %d", duration1);
        } else {
            return String.format("Faster route is Start -> Goal2 -> Goal1 with duration: %d", duration2);
        }
    }

    private long getRouteDuration(String start, String goal, String waypoint) {
        try {
            ResponseEntity<?> response = mapService.getRoute(start, goal, waypoint);
            if (response.getBody() instanceof String) {
                String jsonResponse = (String) response.getBody();
                return JsonParserUtil.extractDuration(jsonResponse);
            }
        } catch (Exception e) {
            System.out.println("Error during route comparison: " + e.getMessage());
        }
        return Long.MAX_VALUE; // 에러 발생 시, 최대값 반환
    }
}
