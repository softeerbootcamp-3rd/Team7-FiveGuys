package com.fiveguys.robocar.service;

import com.fiveguys.robocar.dto.RouteInfo;
import com.fiveguys.robocar.dto.res.RouteResDto;
import com.fiveguys.robocar.dto.res.RouteSoloResDto;
import com.fiveguys.robocar.entity.Car;
import com.fiveguys.robocar.entity.Garage;
import com.fiveguys.robocar.models.CarState;
import com.fiveguys.robocar.repository.CarRepository;
import com.fiveguys.robocar.service.RouteComparisonService.OptimalRoute;
import com.fiveguys.robocar.util.JsonParserUtil.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.fiveguys.robocar.dto.req.CarpoolRegisterReqDto;
import com.fiveguys.robocar.dto.req.CarpoolSuccessReqDto;
import com.fiveguys.robocar.dto.res.CarpoolListUpResDto;
import com.fiveguys.robocar.entity.CarpoolRequest;
import com.fiveguys.robocar.entity.InOperation;
import com.fiveguys.robocar.repository.CarpoolRequestRepository;
import com.fiveguys.robocar.converter.CarpoolRegisterParser;
import com.fiveguys.robocar.repository.InOperationRepository;
import com.fiveguys.robocar.util.CreateCarpoolListUpResDto;
import jakarta.persistence.EntityNotFoundException;
import org.json.JSONException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import java.time.LocalDateTime;

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
    private final InOperationRepository inOperationRepository;
    private final CarRepository carRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

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
                routeInfoGuest = routeService.getRouteInfo(start.toString(), optimalRoute.getSecondDestination(), optimalRoute.getFirstDestination());
            } else {
                // 두 번째 목적지가 호스트의 목적지와 일치
                routeInfoHost = routeService.getRouteInfo(start.toString(), optimalRoute.getSecondDestination(), optimalRoute.getFirstDestination());
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

    public RouteSoloResDto getOptimizedRouteSolo(String startAddress, String destAddress) {
        Coordinate start = mapService.convertAddressToCoordinates(startAddress);
        Coordinate dest = mapService.convertAddressToCoordinates(destAddress);
        Garage nearestGarage = garageService.findNearestGarage(start.toString());
        Car availableCar = carService.findAvailableCar(nearestGarage.getId());

        RouteInfo routeInfo;
        routeInfo = routeService.getRouteInfo(start.toString(), dest.toString(), null);

        return new RouteSoloResDto(
                availableCar.getCarImage(),
                routeInfo.getDuration(),
                availableCar.getCarNumber(),
                availableCar.getCarName(),
                convertCoordinatesToSoloNodes(routeInfo.getPathCoordinates()) // 수정된 부분
        );
    }

    private List<RouteResDto.Node> convertCoordinatesToNodes(List<Coordinate> coordinates) {
        List<RouteResDto.Node> nodes = coordinates.stream()
                .map(coordinate -> new RouteResDto.Node(coordinate.getLatitude(), coordinate.getLongitude()))
                .collect(Collectors.toList());
        return nodes;

    }
    private List<RouteSoloResDto.Node> convertCoordinatesToSoloNodes(List<Coordinate> coordinates) {
        List<RouteSoloResDto.Node> nodes = coordinates.stream()
                .map(coordinate -> new RouteSoloResDto.Node(coordinate.getLatitude(), coordinate.getLongitude()))
                .collect(Collectors.toList());
        return nodes;
    }

    public void saveCarpoolRequest(CarpoolRequest a) {
        carpoolRequestRepository.save(a);
    }

    public CarpoolRequest findCarpoolRequestById(Long id) {
        return carpoolRequestRepository.findById(String.valueOf(id)).orElse(null);
    }

    public CarpoolListUpResDto carpoolListUp(String guestDepartAddress, String guestDestAddress, int maleCount, int femaleCount) {
        return createCarpoolListUpResDto.create(guestDepartAddress, guestDestAddress, maleCount, femaleCount);
    }

    @Transactional
    public void carpoolRegister(CarpoolRegisterReqDto carpoolRegisterReqDto, Long id) {
        Coordinate start = mapService.convertAddressToCoordinates(carpoolRegisterReqDto.getDepartAddress());
        String departAddress = carpoolRegisterReqDto.getDepartAddress();

        Garage garage = garageService.findNearestGarage(start.toString());
        if(garage == null)
            throw new IllegalArgumentException();

        Car car = carService.findAvailableCar(garage.getId());
        if(car == null)
            throw new IllegalArgumentException();


        car.editCarState(CarState.HOLD);
        carRepository.save(car);
        CarpoolRequest carpoolRequest = carpoolRegisterParser.dtoToEntity(carpoolRegisterReqDto, id,car.getId());
        carpoolRequestRepository.save(carpoolRequest);
    }

    @Transactional
    public Long carpoolSuccess(Long id, CarpoolSuccessReqDto carpoolSuccessReqDto) throws JSONException {

        Long guestId = carpoolSuccessReqDto.getGuestId();
        String guestDestAddress = carpoolSuccessReqDto.getGuestDestAddress();

        CarpoolRequest carpoolRequest = carpoolRequestRepository.findById(String.valueOf(id)).orElseThrow(EntityNotFoundException::new);

        // car State를 IN_OPERATION으로 변경
        Car car = carRepository.findById(carpoolRequest.getCarId()).orElseThrow(EntityNotFoundException::new);
        car.editCarState(CarState.IN_OPERATION);

        InOperation inOperation = InOperation.builder()
                .departureAddress(carpoolRequest.getHostDepartAddress())
                .hostDestAddress(carpoolRequest.getHostDestAddress())
                .guestDestAddress(guestDestAddress)
                .hostId(id)
                .guestId(guestId)
                .departureTime(LocalDateTime.now())
                .carId(carpoolRequest.getCarId())
                //TODO
                // 얼마나 갈리는지 아래부분 추가
//                .estimatedHostArrivalTime()
//                .estimatedGuestArrivalTime()
                .build();

        Long inOperationId = inOperationRepository.save(inOperation).getId();

        firebaseCloudMessageService.pushCarpoolAccept(guestId,inOperationId);

        carpoolRequestRepository.deleteById(String.valueOf(id));

        return inOperationId;
    }

    @Transactional
    public void carpoolRequestCancel(Long id) throws Exception {

        CarpoolRequest carpoolRequest= carpoolRequestRepository.findById(String.valueOf(id)).orElseThrow(EntityNotFoundException::new);
        carpoolRequestRepository.deleteById(String.valueOf(id));
        Car car = carRepository.findById(carpoolRequest.getCarId()).orElseThrow(Exception::new);
        car.editCarState(CarState.READY);

        carRepository.save(car);
    }
}
