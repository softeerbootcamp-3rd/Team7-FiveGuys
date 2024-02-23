package com.fiveguys.robocar.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class OperationService {
    private final CarpoolRequestRepository carpoolRequestRepository;
    private final CarpoolRegisterParser carpoolRegisterParser;
    private final RouteService routeService;
    private final MapService mapService;
    private final RouteComparisonService routeComparisonService;
    private final CreateCarpoolListUpResDto createCarpoolListUpResDto;
    private final InOperationRepository inOperationRepository;

    private final FirebaseCloudMessageService firebaseCloudMessageService;
    @Autowired
    public OperationService(CarpoolRequestRepository carpoolRequestRepository,
                            CarpoolRegisterParser carpoolRegisterParser,
                            MapService mapService,RouteService routeService,
                            RouteComparisonService routeComparisonService,
                            CreateCarpoolListUpResDto createCarpoolListUpResDto,
                            InOperationRepository inOperationRepository,
                            FirebaseCloudMessageService firebaseCloudMessageService){

        this.carpoolRequestRepository = carpoolRequestRepository;
        this.carpoolRegisterParser = carpoolRegisterParser;
        this.mapService = mapService;
        this.routeService = routeService;
        this.routeComparisonService = routeComparisonService;
        this.createCarpoolListUpResDto = createCarpoolListUpResDto;
        this.inOperationRepository = inOperationRepository;
        this.firebaseCloudMessageService = firebaseCloudMessageService;

    }

    public void saveCarpoolRequest(CarpoolRequest carpoolRequest){
        carpoolRequestRepository.save(carpoolRequest);
    }

    public CarpoolRequest findCarpoolRequestById(Long id){
        return carpoolRequestRepository.findById(String.valueOf(id)).orElse(null);
    }

    public CarpoolListUpResDto carpoolListUp(String guestDepartAddress, String guestDestAddress,int maleCount, int femaleCount) {

        return createCarpoolListUpResDto.create(guestDepartAddress,guestDestAddress, maleCount,femaleCount);
    }

    @Transactional
    public void carpoolRegister(CarpoolRegisterReqDto carpoolRegisterReqDto, Long id) {
        // 여기서 자동차 매칭 시켜야 함
        // 주변 차고지에서 차량 아이디 리턴 + 자동차 상태 변경
        CarpoolRequest carpoolRequest = carpoolRegisterParser.dtoToEntity(carpoolRegisterReqDto, id);
        carpoolRequestRepository.save(carpoolRequest);
    }

    @Transactional
    public void carpoolSuccess(Long id, CarpoolSuccessReqDto carpoolSuccessReqDto) throws JSONException {

        Long guestId = carpoolSuccessReqDto.getGuestId();
        String guestDestAddress = carpoolSuccessReqDto.getGuestDestAddress();

        CarpoolRequest carpoolRequest = carpoolRequestRepository.findById(String.valueOf(id)).orElseThrow(EntityNotFoundException::new);

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
                .estimatedHostArrivalTime(LocalDateTime.now())
                .estimatedGuestArrivalTime(LocalDateTime.now())
                .build();




        Long inOperationId = inOperationRepository.save(inOperation).getId();

        firebaseCloudMessageService.pushCarpoolAccept(guestId,inOperationId);

        carpoolRequestRepository.deleteById(String.valueOf(id));

    }
}
