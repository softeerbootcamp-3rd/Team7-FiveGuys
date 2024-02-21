package com.fiveguys.robocar.service;

import com.fiveguys.robocar.dto.RouteInfo;
import com.fiveguys.robocar.dto.res.RouteResDto;
import com.fiveguys.robocar.service.RouteComparisonService.OptimalRoute;
import com.fiveguys.robocar.util.JsonParserUtil.Coordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationService {

    private final MapService mapService;
    private final RouteComparisonService routeComparisonService;
    private final RouteService routeService;

    @Autowired
    public OperationService(MapService mapService, RouteComparisonService routeComparisonService, RouteService routeService) {
        this.mapService = mapService;
        this.routeComparisonService = routeComparisonService;
        this.routeService = routeService;
    }

    public RouteResDto getOptimizedRoute(String startAddress, String hostDestAddress, String guestDestAddress) {
        Coordinate start = mapService.convertAddressToCoordinates(startAddress);
        Coordinate hostDest = mapService.convertAddressToCoordinates(hostDestAddress);
        Coordinate guestDest = guestDestAddress != null ? mapService.convertAddressToCoordinates(guestDestAddress) : null;

        // 최적의 경로 결정
        OptimalRoute optimalRoute = routeComparisonService.determineOptimalRoute(start.toString(), hostDest.toString(), guestDest != null ? guestDest.toString() : null);

        // 최적 경로에 대한 상세 정보 조회
        RouteInfo routeInfoHost = routeService.getRouteInfo(start.toString(), optimalRoute.getFirstDestination(), optimalRoute.getSecondDestination());
        RouteInfo routeInfoGuest = guestDest != null ? routeService.getRouteInfo(start.toString(), optimalRoute.getSecondDestination(), null) : null;

        // 결과를 RouteResDto 객체로 매핑
        return new RouteResDto(
                // ID, 경로 노드 등 필요한 정보를 적절히 설정
        );
    }
}
