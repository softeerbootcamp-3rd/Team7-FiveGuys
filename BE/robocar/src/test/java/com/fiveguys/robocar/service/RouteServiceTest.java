package com.fiveguys.robocar.service;

import com.fiveguys.robocar.dto.RouteInfo;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RouteServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(RouteServiceTest.class);

    @Autowired
    private RouteService routeService;

    @Test
    public void testGetRouteInfo() throws Exception {
        String start = "127.1058342,37.359708";
        String goal1 = "127.1230000,37.323";
        String goal2 = null; // goal2를 사용하지 않는 경우 null로 설정

        // 경로 정보 가져오기 로직을 테스트
        RouteInfo routeInfo = routeService.getRouteInfo(start, goal1, goal2);

        // RouteInfo 객체와 그 필드들이 정상적으로 설정되었는지 검증
        assertNotNull(routeInfo, "RouteInfo 객체가 null입니다.");
        assertTrue(routeInfo.getDuration() > 0, "Duration이 0 이하입니다.");
        assertTrue(routeInfo.getTaxiFare() > 0, "TaxiFare가 0 이하입니다.");
        assertNotNull(routeInfo.getPathCoordinates(), "PathCoordinates가 null입니다.");
        assertTrue(routeInfo.getPathCoordinates().size() > 0, "PathCoordinates가 비어있습니다.");

        // RouteInfo 객체의 필드 값들을 로깅
        logger.info("RouteInfo Details:");
        logger.info("Duration: {}", routeInfo.getDuration());
        logger.info("Taxi Fare: {}", routeInfo.getTaxiFare());
        logger.info("Path Coordinates:");
        routeInfo.getPathCoordinates().forEach(coordinate ->
                logger.info(" - Latitude: {}, Longitude: {}", coordinate.getLatitude(), coordinate.getLongitude())
        );
    }
}
