package com.fiveguys.robocar.service;



import com.fiveguys.robocar.dto.req.CarpoolRegisterReqDto;
import com.fiveguys.robocar.dto.req.CarpoolSuccessReqDto;
import com.fiveguys.robocar.dto.res.CarpoolListUpResDto;
import com.fiveguys.robocar.entity.CarpoolRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class OperationServiceTest {

    @Autowired
    private OperationService service;
    @Autowired
    MapService mapService;
    CarpoolRequest carpoolRequest = CarpoolRequest.builder()
            .id(1L)
            .hostNickname("hihi")
            .departLatitude(37.3595963)
            .departLongitude(127.1054328)
            .hostDestLatitude(37.5142064)
            .hostDestLongitude(127.0314638)
            .hostDepartAddress("분당구 불정로 6")
            .hostDestAddress("서울 강남구 학동로 180")
            .maleCount(1)
            .femaleCount(2)
            .build();


    @Test
    @DisplayName("redis 연결성 + repository 테스트")
    public void redisSave(){
        service.saveCarpoolRequest(carpoolRequest);
        CarpoolRequest carpoolRequest1 = service.findCarpoolRequestById(1L);

        log.info(carpoolRequest1.getHostNickname());
        assertEquals(carpoolRequest.getDepartLatitude(),carpoolRequest1.getDepartLatitude());
    }


    @Test
    @DisplayName("carpoolSuccess 테스트")
    public void redisDelete(){
        //given
        service.saveCarpoolRequest(carpoolRequest);

        //when
        Long id = 1L;
        CarpoolSuccessReqDto carpoolSuccessReqDto = new CarpoolSuccessReqDto(2L, "서울시 강남구");
        service.carpoolSuccess(id, carpoolSuccessReqDto);
        CarpoolRequest carpoolRequest = service.findCarpoolRequestById(id);

        //then
        assertNull(carpoolRequest);
    }

    @Test
    @DisplayName("carpoolRegister 테스트")
    public void redisRegister(){
        //given
        CarpoolRegisterReqDto carpoolRegisterReqDto = new CarpoolRegisterReqDto("서울시 강남구","서울시 동작구",1,2);
        Long id = 2L;

        //when
        service.carpoolRegister(carpoolRegisterReqDto, id);

        CarpoolRequest carpoolRequest1 = service.findCarpoolRequestById(id);
        //then
        //check redis
    }

    @Test
    @DisplayName("carpoolListUp 테스트")
    public void redisListUp(){
        CarpoolListUpResDto response;



        response = service.carpoolListUp("분당구 불정로 6","논현동 215");
        System.out.println(response.getList().size());
        for(CarpoolListUpResDto.CarpoolItem itm:response.getList()){
            System.out.println(itm.getHostNickname());
        }
    }

}
