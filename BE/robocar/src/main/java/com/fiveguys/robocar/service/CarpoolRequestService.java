package com.fiveguys.robocar.service;

import com.fiveguys.robocar.dto.res.CarpoolListUpResDto;
import com.fiveguys.robocar.entity.CarpoolRequest;
import com.fiveguys.robocar.repository.CarpoolRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarpoolRequestService {
    private final CarpoolRequestRepository carpoolRequestRepository;

    @Autowired
    public CarpoolRequestService(CarpoolRequestRepository carpoolRequestRepository){
        this.carpoolRequestRepository = carpoolRequestRepository;
    }

    public void saveCarpoolRequest(CarpoolRequest carpoolRequest){
        carpoolRequestRepository.save(carpoolRequest);
    }

    public CarpoolRequest findCarpoolRequestById(String id){
        return carpoolRequestRepository.findById(id).orElse(null);
    }

    public CarpoolListUpResDto carpoolListUp(String guestDepartAddress, String guestDestAddress) {
        //TODO
        // 예상 비용 함수로 price값 구하기
        Integer price = 1000;

        CarpoolListUpResDto carpoolListUpResDto = new CarpoolListUpResDto(price);
        Iterable<CarpoolRequest> iterableRequests = carpoolRequestRepository.findAll();

        CarpoolListUpResDto.CarpoolItem carpoolItem = null;
        for(CarpoolRequest req : iterableRequests){
            carpoolItem.builder()
                    .hostId(req.getHostId())
                    .hostNickname(req.getHostNickname())
                    .departLongitude(req.getDepartLongitude())
                    .departLatitude(req.getDepartLatitude())
                    .hostDestLongitude(req.getHostDestLongitude())
                    .hostDestLatitude(req.getHostDestLatitude())
                    .hostDepartAddress(req.getHostDepartAddress())
                    .hostDestAddress(req.getHostDestAddress())
                    .maleCount(req.getMaleCount())
                    .femaleCount(req.getFemaleCount())
                    .estimatedTime(111)
                    .estimatedPrice(111)
                    .build();
        }

        return carpoolListUpResDto;


    }
}
