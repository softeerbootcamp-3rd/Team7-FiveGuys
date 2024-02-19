package com.fiveguys.robocar.util;

import com.fiveguys.robocar.dto.req.CarpoolRegisterReqDto;
import com.fiveguys.robocar.entity.CarpoolRequest;
import com.fiveguys.robocar.entity.User;
import com.fiveguys.robocar.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CarpoolRegisterParser {

    UserRepository userRepository;

    @Autowired
    CarpoolRegisterParser(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public CarpoolRequest dtoToEntity(CarpoolRegisterReqDto carpoolRegisterReqDto){
        String hostDepartAddress = carpoolRegisterReqDto.getDepartAddress();
        Double departLatitude = 0.0;
        Double departLongitude = 0.0;

        String hostDestAddress = carpoolRegisterReqDto.getDestAddress();
        Double hostDestLatitude = 10.0;
        Double hostDestLongitude = 10.0;


        Long id = carpoolRegisterReqDto.getId();

        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        String hostNickname = user.getNickname();

        Integer maleCount = carpoolRegisterReqDto.getMaleCount();
        Integer femaleCount = carpoolRegisterReqDto.getFemaleCount();

        CarpoolRequest carpoolRequest = CarpoolRequest.builder()
                .id(id)
                .hostNickname(hostNickname)
                .departLatitude(departLatitude)
                .departLongitude(departLongitude)
                .hostDestLatitude(hostDestLatitude)
                .hostDestLongitude(hostDestLongitude)
                .hostDepartAddress(hostDepartAddress)
                .hostDestAddress(hostDestAddress)
                .maleCount(maleCount)
                .femaleCount(femaleCount)
                .build();
        return carpoolRequest;
    }
}
