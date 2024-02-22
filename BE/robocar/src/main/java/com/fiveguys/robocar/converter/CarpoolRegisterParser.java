package com.fiveguys.robocar.converter;

import com.fiveguys.robocar.dto.req.CarpoolRegisterReqDto;
import com.fiveguys.robocar.entity.CarpoolRequest;
import com.fiveguys.robocar.entity.User;
import com.fiveguys.robocar.models.CarType;
import com.fiveguys.robocar.repository.UserRepository;
import com.fiveguys.robocar.service.MapService;
import com.fiveguys.robocar.util.JsonParserUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CarpoolRegisterParser {

    UserRepository userRepository;
    MapService mapService;

    @Autowired
    CarpoolRegisterParser(UserRepository userRepository, MapService mapService){
        this.userRepository = userRepository;
        this.mapService = mapService;
    }

    @Transactional(readOnly = true)
    public CarpoolRequest dtoToEntity(CarpoolRegisterReqDto carpoolRegisterReqDto, Long id){
        JsonParserUtil.Coordinate coordinate;

        String hostDepartAddress = carpoolRegisterReqDto.getDepartAddress();
        coordinate = mapService.convertAddressToCoordinates(hostDepartAddress);
        Double departLatitude = coordinate.getLatitude();
        Double departLongitude = coordinate.getLongitude();


        String hostDestAddress = carpoolRegisterReqDto.getDestAddress();
        coordinate = mapService.convertAddressToCoordinates(hostDestAddress);
        Double hostDestLatitude = coordinate.getLatitude();
        Double hostDestLongitude = coordinate.getLongitude();

        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        String hostNickname = user.getNickname();

        Integer maleCount = carpoolRegisterReqDto.getMaleCount();
        Integer femaleCount = carpoolRegisterReqDto.getFemaleCount();

        CarType carType = carpoolRegisterReqDto.getCarType();

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
                .carType(carType)
                .build();
        return carpoolRequest;
    }
}
