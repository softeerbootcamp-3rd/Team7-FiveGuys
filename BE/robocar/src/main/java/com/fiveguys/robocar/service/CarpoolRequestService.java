package com.fiveguys.robocar.service;

import com.fiveguys.robocar.dto.req.CarpoolRegisterReqDto;
import com.fiveguys.robocar.dto.req.CarpoolSuccessReqDto;
import com.fiveguys.robocar.dto.res.CarpoolListUpResDto;
import com.fiveguys.robocar.entity.CarpoolRequest;
import com.fiveguys.robocar.entity.User;
import com.fiveguys.robocar.repository.CarpoolRequestRepository;
import com.fiveguys.robocar.repository.UserRepository;
import com.fiveguys.robocar.util.CarpoolRegisterParser;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarpoolRequestService {
    private final CarpoolRequestRepository carpoolRequestRepository;
    private final CarpoolRegisterParser carpoolRegisterParser;

    @Autowired
    public CarpoolRequestService(CarpoolRequestRepository carpoolRequestRepository, CarpoolRegisterParser carpoolRegisterParser){
        this.carpoolRequestRepository = carpoolRequestRepository;
        this.carpoolRegisterParser = carpoolRegisterParser;
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

        carpoolListUpResDto.makeCarpoolList(iterableRequests);

        carpoolListUpResDto.doSortByCoordinate(guestDepartAddress, guestDestAddress);

        return carpoolListUpResDto;

    }

    public void carPoolRegister(CarpoolRegisterReqDto carpoolRegisterReqDto, Long id) {
        CarpoolRequest carpoolRequest = carpoolRegisterParser.dtoToEntity(carpoolRegisterReqDto, id);
        carpoolRequestRepository.save(carpoolRequest);
    }

    @Transactional
    public void carpoolSuccess(Long id, CarpoolSuccessReqDto carpoolSuccessReqDto) {

        Long guestId = carpoolSuccessReqDto.getGuestId();
        String guestDestAddress = carpoolSuccessReqDto.getGuestDestAddress();

        //TODO
        // 주소 기반으로 운행정보 생성 후 운행정보 디비에 저장
        // 게스트와 호스트에게 호출정보 푸시

        carpoolRequestRepository.deleteById(String.valueOf(id));

    }
}
