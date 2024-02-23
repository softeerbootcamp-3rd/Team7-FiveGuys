package com.fiveguys.robocar.util;

import com.fiveguys.robocar.converter.CarpoolRegisterParser;
import com.fiveguys.robocar.dto.RouteInfo;
import com.fiveguys.robocar.dto.res.CarpoolListUpResDto;
import com.fiveguys.robocar.entity.CarpoolRequest;
import com.fiveguys.robocar.repository.CarpoolRequestRepository;
import com.fiveguys.robocar.service.MapService;
import com.fiveguys.robocar.service.RouteComparisonService;
import com.fiveguys.robocar.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateCarpoolListUpResDto {

    private final int MAX_RANGE_TIME;
    private final int MAX_LIST_LENGTH;

    private final CarpoolRequestRepository carpoolRequestRepository;
    private final RouteService routeService;
    private final MapService mapService;
    private final RouteComparisonService routeComparisonService;
    @Autowired
    public CreateCarpoolListUpResDto(CarpoolRequestRepository carpoolRequestRepository, CarpoolRegisterParser carpoolRegisterParser, MapService mapService, RouteService routeService, RouteComparisonService routeComparisonService){
        this.carpoolRequestRepository = carpoolRequestRepository;
        this.mapService = mapService;
        this.routeService = routeService;
        this.routeComparisonService = routeComparisonService;
        this.MAX_RANGE_TIME = 10;
        this.MAX_LIST_LENGTH = 50;
    }

    public CarpoolListUpResDto create(String guestDepartAddress, String guestDestAddress, int guestMaleCount, int guestFemaleCount){

        JsonParserUtil.Coordinate coordinate;
        coordinate = mapService.convertAddressToCoordinates(guestDepartAddress);
        String guestDepartCoordinate = String.format("%f,%f",coordinate.getLongitude(),coordinate.getLatitude());
        coordinate = mapService.convertAddressToCoordinates(guestDestAddress);
        String guestDestCoordinate = String.format("%f,%f",coordinate.getLongitude(),coordinate.getLatitude());
        if(guestDestCoordinate.equals(guestDepartCoordinate))
            throw new IllegalArgumentException();
        Long price = routeService.getRouteInfo(guestDepartCoordinate, guestDestCoordinate, null).getTaxiFare();

        CarpoolListUpResDto carpoolListUpResDto = new CarpoolListUpResDto(price);

        Iterable<CarpoolRequest> iterableRequests = carpoolRequestRepository.findAll();
        CarpoolListUpResDto.CarpoolItem carpoolItem;
        RouteComparisonService.OptimalRoute optimalRoute;
        String hostDepartCoordinate;
        String hostDestCoordinate;
        long duration;
        long taxifare;
        RouteInfo routeInfo;


        for(CarpoolRequest req : iterableRequests){
            if(req == null)
                continue;

            hostDepartCoordinate = String.format("%f,%f",req.getDepartLongitude(),req.getDepartLatitude());

            //10분 초과 거리는 제외

            try {
                if (!hostDepartCoordinate.equals(guestDepartCoordinate) && routeService.getRouteInfo(hostDepartCoordinate, guestDepartCoordinate, null).getDuration() / (1000 * 60) > MAX_RANGE_TIME)
                    continue;
            } catch (Exception e){}

            //인원수 초과시 제외
            if(req.getFemaleCount()+req.getMaleCount()+guestMaleCount+guestFemaleCount > req.getCarType().getCapacity())
                continue;


            //고려된 예외 상황
            // 1. 출발지가 일치할 때
            // 2. 도착지가 일치할 때
            hostDestCoordinate = String.format("%f,%f",req.getHostDestLongitude(),req.getHostDestLatitude());


            optimalRoute = routeComparisonService.determineOptimalRoute(hostDepartCoordinate,hostDestCoordinate,guestDestCoordinate);
            routeInfo = routeService.getRouteInfo(hostDepartCoordinate, optimalRoute.getFirstDestination(), optimalRoute.getSecondDestination());
            duration = routeInfo.getDuration()/60/1000;

            taxifare = routeInfo.getTaxiFare()/2;
            carpoolItem = CarpoolListUpResDto.CarpoolItem.builder()
                    .hostId(req.getId())
                    .hostNickname(req.getHostNickname())
                    .departLongitude(req.getDepartLongitude())
                    .departLatitude(req.getDepartLatitude())
                    .hostDestLongitude(req.getHostDestLongitude())
                    .hostDestLatitude(req.getHostDestLatitude())
                    .hostDepartAddress(req.getHostDepartAddress())
                    .hostDestAddress(req.getHostDestAddress())
                    .maleCount(req.getMaleCount())
                    .femaleCount(req.getFemaleCount())
                    .estimatedTime(duration)
                    .estimatedPrice(taxifare)
                    .build();
            carpoolListUpResDto.addCarpoolItem(carpoolItem);

        }

        carpoolListUpResDto.doSortByEstimatedTime();
        carpoolListUpResDto.trimByLengthOf(MAX_LIST_LENGTH);
        return carpoolListUpResDto;
    }

}
