package com.fiveguys.robocar.service;



import com.fiveguys.robocar.entity.CarpoolRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OperationServiceTest {

    @Autowired
    private OperationService service;


//    @Test
//    @DisplayName("redis 연결성 + service 테스트")
//    public void runRedis(){
//        CarpoolRequest carpoolRequest = new CarpoolRequest("1", LocalDateTime.now(), 1,1,0,0);
//        //레디스는 영속성 컨텍스트를 만들지 않음
//        service.saveCarpoolRequest(carpoolRequest);
//        CarpoolRequest carpoolRequest1 = service.findCarpoolRequestById("1");
//        assertEquals(carpoolRequest.getDepartLatitude(),carpoolRequest1.getDepartLatitude());
//
//    }

}
