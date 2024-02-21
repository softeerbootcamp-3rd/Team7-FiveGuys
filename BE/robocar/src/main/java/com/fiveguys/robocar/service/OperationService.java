package com.fiveguys.robocar.service;

import com.fiveguys.robocar.dto.RouteInfo;
import com.fiveguys.robocar.dto.res.RouteResDto;
import com.fiveguys.robocar.entity.Car;
import com.fiveguys.robocar.entity.Garage;
import com.fiveguys.robocar.service.RouteComparisonService.OptimalRoute;
import com.fiveguys.robocar.util.JsonParserUtil.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperationService {

    private final MapService mapService;
    private final GarageService garageService;
    private final RouteComparisonService routeComparisonService;
    private final RouteService routeService;
    private final CarService carService;


    public RouteResDto getOptimizedRoute(String startAddress, String hostDestAddress, String guestDestAddress, Long hostId, Long guestId) {
        Coordinate start = mapService.convertAddressToCoordinates(startAddress);
        Coordinate hostDest = mapService.convertAddressToCoordinates(hostDestAddress);
        Coordinate guestDest = guestDestAddress != null ? mapService.convertAddressToCoordinates(guestDestAddress) : null;
        Garage nearestGarage = garageService.findNearestGarage(start.toString());
        Car availableCar = carService.findAvailableCar(nearestGarage.getId());
        // 최적의 경로 결정
        OptimalRoute optimalRoute = routeComparisonService.determineOptimalRoute(start.toString(), hostDest.toString(), guestDest != null ? guestDest.toString() : null);

        // 최적 경로에 대한 상세 정보 조회
        RouteInfo routeInfoHost = routeService.getRouteInfo(start.toString(), optimalRoute.getFirstDestination(), optimalRoute.getSecondDestination());
        RouteInfo routeInfoGuest = guestDest != null ? routeService.getRouteInfo(start.toString(), optimalRoute.getSecondDestination(), null) : null;

        return new RouteResDto(
                hostId,
                guestId,
                availableCar.getCarImage(),
                routeInfoHost.getDuration(),
                routeInfoGuest != null ? routeInfoGuest.getDuration() : null,
                availableCar.getCarNumber(),
                availableCar.getCarName(),
                convertCoordinatesToNodes(routeInfoHost.getPathCoordinates()),
                routeInfoGuest != null ? convertCoordinatesToNodes(routeInfoGuest.getPathCoordinates()) : null
        );
    }

    private List<RouteResDto.Node> convertCoordinatesToNodes(List<Coordinate> coordinates) {
        List<RouteResDto.Node> nodes = coordinates.stream()
                .map(coordinate -> new RouteResDto.Node(coordinate.getLatitude(), coordinate.getLongitude()))
                .collect(Collectors.toList());
        return nodes;
    }
}
