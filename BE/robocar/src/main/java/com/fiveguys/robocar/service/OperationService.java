package com.fiveguys.robocar.service;

import com.fiveguys.robocar.dto.req.CarpoolRegisterReqDto;
import com.fiveguys.robocar.dto.req.CarpoolSuccessReqDto;
import com.fiveguys.robocar.dto.res.CarpoolListUpResDto;
import com.fiveguys.robocar.entity.CarpoolRequest;
import com.fiveguys.robocar.repository.CarpoolRequestRepository;
import com.fiveguys.robocar.converter.CarpoolRegisterParser;
import com.fiveguys.robocar.util.CreateCarpoolListUpResDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public CarpoolRequest findCarpoolRequestById(Long id){
        return carpoolRequestRepository.findById(String.valueOf(id)).orElse(null);
    }

    public CarpoolListUpResDto carpoolListUp(String guestDepartAddress, String guestDestAddress) {

        return createCarpoolListUpResDto.create(guestDepartAddress,guestDestAddress);
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
