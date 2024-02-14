package com.fiveguys.robocar.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RouteServiceTest {

    @Autowired
    private RouteService routeService;

    @Test
    public void testGetRouteInfo() throws Exception {
        String start = "127.1058342,37.359708";
        String goal1 = "127.1230000,37.323";
        //String guestDestCoordinate = "127.1156,37.3456";
        String goal2 = null;

        // 경로 정보 가져오기 로직을 테스트
        RouteService.RouteInfo routeInfo = routeService.getRouteInfo(start, goal1, goal2);

        // RouteInfo 객체와 그 필드들이 정상적으로 설정되었는지 검증
        assertNotNull(routeInfo, "RouteInfo 객체가 null입니다.");
        assertTrue(routeInfo.getDuration() > 0, "Duration이 0 이하입니다.");
        assertTrue(routeInfo.getTaxiFare() > 0, "TaxiFare가 0 이하입니다.");
        assertNotNull(routeInfo.getPathCoordinates(), "PathCoordinates가 null입니다.");
        assertTrue(routeInfo.getPathCoordinates().size() > 0, "PathCoordinates가 비어있습니다.");

        // RouteInfo 객체의 필드 값들을 출력
        System.out.println("RouteInfo Details:");
        System.out.println("Duration: " + routeInfo.getDuration());
        System.out.println("Taxi Fare: " + routeInfo.getTaxiFare());
        System.out.println("Path Coordinates:");
        routeInfo.getPathCoordinates().forEach(coordinate ->
                System.out.println(" - Latitude: " + coordinate.getLatitude() + ", Longitude: " + coordinate.getLongitude())
        );
    }
}
