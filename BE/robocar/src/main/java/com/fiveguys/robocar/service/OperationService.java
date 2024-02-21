package com.fiveguys.robocar.service;

import com.fiveguys.robocar.dto.req.CarpoolRegisterReqDto;
import com.fiveguys.robocar.dto.res.CarpoolListUpResDto;
import com.fiveguys.robocar.entity.CarpoolRequest;
import com.fiveguys.robocar.repository.CarpoolRequestRepository;
import com.fiveguys.robocar.converter.CarpoolRegisterParser;
import com.fiveguys.robocar.util.CreateCarpoolListUpResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationService {
    private final CarpoolRequestRepository carpoolRequestRepository;
    private final CarpoolRegisterParser carpoolRegisterParser;
    private final RouteService routeService;
    private final MapService mapService;
    private final RouteComparisonService routeComparisonService;
    private final CreateCarpoolListUpResDto createCarpoolListUpResDto;

    @Autowired
    public OperationService(CarpoolRequestRepository carpoolRequestRepository, CarpoolRegisterParser carpoolRegisterParser, MapService mapService,RouteService routeService,RouteComparisonService routeComparisonService,CreateCarpoolListUpResDto createCarpoolListUpResDto){
        this.carpoolRequestRepository = carpoolRequestRepository;
        this.carpoolRegisterParser = carpoolRegisterParser;
        this.mapService = mapService;
        this.routeService = routeService;
        this.routeComparisonService = routeComparisonService;
        this.createCarpoolListUpResDto = createCarpoolListUpResDto;
    }

    public void saveCarpoolRequest(CarpoolRequest carpoolRequest){
        carpoolRequestRepository.save(carpoolRequest);
    }

    public CarpoolRequest findCarpoolRequestById(String id){
        return carpoolRequestRepository.findById(id).orElse(null);
    }

    public CarpoolListUpResDto carpoolListUp(String guestDepartAddress, String guestDestAddress) {
        //TODO
        // 인원수 제한은 설정 안되어 있음
        return createCarpoolListUpResDto.create(guestDepartAddress,guestDestAddress);
    }

    public void carPoolRegister(CarpoolRegisterReqDto carpoolRegisterReqDto) {
        CarpoolRequest carpoolRequest = carpoolRegisterParser.dtoToEntity(carpoolRegisterReqDto);
        carpoolRequestRepository.save(carpoolRequest);
    }
}
