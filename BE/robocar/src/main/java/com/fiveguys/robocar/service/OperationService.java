package com.fiveguys.robocar.service;

import com.fiveguys.robocar.dto.RouteInfo;
import com.fiveguys.robocar.dto.res.RouteResDto;
import com.fiveguys.robocar.entity.Car;
import com.fiveguys.robocar.entity.Garage;
import com.fiveguys.robocar.service.RouteComparisonService.OptimalRoute;
import com.fiveguys.robocar.util.JsonParserUtil.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.fiveguys.robocar.dto.req.CarpoolRegisterReqDto;
import com.fiveguys.robocar.dto.req.CarpoolSuccessReqDto;
import com.fiveguys.robocar.dto.res.CarpoolListUpResDto;
import com.fiveguys.robocar.entity.CarpoolRequest;
import com.fiveguys.robocar.repository.CarpoolRequestRepository;
import com.fiveguys.robocar.converter.CarpoolRegisterParser;
import com.fiveguys.robocar.util.CreateCarpoolListUpResDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

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
    private final CarpoolRequestRepository carpoolRequestRepository;
    private final CarpoolRegisterParser carpoolRegisterParser;
    private final CreateCarpoolListUpResDto createCarpoolListUpResDto;


    public RouteResDto getOptimizedRoute(String startAddress, String hostDestAddress, String guestDestAddress, Long hostId, Long guestId) {
        Coordinate start = mapService.convertAddressToCoordinates(startAddress);
        Coordinate hostDest = mapService.convertAddressToCoordinates(hostDestAddress);
        Coordinate guestDest = guestDestAddress != null ? mapService.convertAddressToCoordinates(guestDestAddress) : null;
        Garage nearestGarage = garageService.findNearestGarage(start.toString());
        Car availableCar = carService.findAvailableCar(nearestGarage.getId());

        RouteInfo routeInfoHost;
        RouteInfo routeInfoGuest = null;

        if (guestDest != null) {
            // 최적의 경로 결정
            OptimalRoute optimalRoute = routeComparisonService.determineOptimalRoute(start.toString(), hostDest.toString(), guestDest.toString());

            boolean isFirstDestinationHost = optimalRoute.getFirstDestination().equals(hostDest.toString());
            if (isFirstDestinationHost) {
                // 첫 번째 목적지가 호스트의 목적지와 일치
                routeInfoHost = routeService.getRouteInfo(start.toString(), optimalRoute.getFirstDestination(), null);
                routeInfoGuest = routeService.getRouteInfo(start.toString(), optimalRoute.getFirstDestination(), optimalRoute.getSecondDestination());
            } else {
                // 두 번째 목적지가 호스트의 목적지와 일치
                routeInfoHost = routeService.getRouteInfo(start.toString(), optimalRoute.getFirstDestination(), optimalRoute.getSecondDestination());
                routeInfoGuest = routeService.getRouteInfo(start.toString(), optimalRoute.getFirstDestination(), null);
            }
        } else {
            // 게스트 목적지 없이 호스트의 경로만 조회
            routeInfoHost = routeService.getRouteInfo(start.toString(), hostDest.toString(), null);
        }
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

    public void saveCarpoolRequest(CarpoolRequest carpoolRequest) {
        carpoolRequestRepository.save(carpoolRequest);
    }

    public CarpoolRequest findCarpoolRequestById(Long id) {
        return carpoolRequestRepository.findById(String.valueOf(id)).orElse(null);
    }

    public CarpoolListUpResDto carpoolListUp(String guestDepartAddress, String guestDestAddress, int maleCount, int femaleCount) {

        return createCarpoolListUpResDto.create(guestDepartAddress, guestDestAddress, maleCount, femaleCount);
    }

    public void carpoolRegister(CarpoolRegisterReqDto carpoolRegisterReqDto, Long id) {
        CarpoolRequest carpoolRequest = carpoolRegisterParser.dtoToEntity(carpoolRegisterReqDto, id);
        carpoolRequestRepository.save(carpoolRequest);
    }

    @Transactional
    public void carpoolSuccess(Long id, CarpoolSuccessReqDto carpoolSuccessReqDto) {

        Long guestId = carpoolSuccessReqDto.getGuestId();
        String guestDestAddress = carpoolSuccessReqDto.getGuestDestAddress();

        carpoolRequestRepository.findById(String.valueOf(id)).orElseThrow(EntityNotFoundException::new);
        //TODO
        // 주소 기반으로 운행정보 생성 후 운행정보 디비에 저장
        // 게스트와 호스트에게 호출정보 푸시
        // inoperation에 저장
        //

        //호스트랑 게스트에게 공통으로 보낼 것:
        //hostid, guestid,출발주소, 호스트도착주소, 게스트 도착주소

        carpoolRequestRepository.deleteById(String.valueOf(id));

    }
}
