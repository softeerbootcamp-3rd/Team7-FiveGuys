package com.fiveguys.robocar.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RouteComparisonServiceTest {

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

        // 성공 메시지 출력
        System.out.println("테스트 성공: 최적 경로는 " + optimalRoute.getFirstDestination() + "에서 " + optimalRoute.getSecondDestination() + "으로 결정되었습니다.");
    }
}
