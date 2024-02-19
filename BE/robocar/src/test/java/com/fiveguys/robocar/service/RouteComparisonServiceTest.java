package com.fiveguys.robocar.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RouteComparisonServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(RouteComparisonServiceTest.class);

    @Autowired
    private RouteComparisonService routeComparisonService;

    @Test
    public void testDetermineOptimalRoute() {
        String start = "127.1058342,37.359708";
        String hostDestCoordinate = "128.1230000,38.323";
        String guestDestCoordinate = "127.1156,37.3456";

        // 최적 경로 결정 로직을 테스트
        RouteComparisonService.OptimalRoute optimalRoute = routeComparisonService.determineOptimalRoute(start, hostDestCoordinate, guestDestCoordinate);

        // 결과 검증
        assertNotNull(optimalRoute, "OptimalRoute 객체가 null입니다.");
        assertNotNull(optimalRoute.getFirstDestination(), "첫 번째 목적지가 null입니다.");
        assertNotNull(optimalRoute.getSecondDestination(), "두 번째 목적지가 null입니다.");

        // 성공 메시지 로깅
        logger.info("테스트 성공: 최적 경로는 {}에서 {}으로 결정되었습니다.", optimalRoute.getFirstDestination(), optimalRoute.getSecondDestination());
    }
}
