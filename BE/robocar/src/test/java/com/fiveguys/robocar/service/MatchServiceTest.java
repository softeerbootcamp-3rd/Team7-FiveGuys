package com.fiveguys.robocar.service;



import com.fiveguys.robocar.entity.Match;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MatchServiceTest {

    @Autowired
    private MatchService service;


    @Test
    @DisplayName("redis 연결성 + service 테스트")
    public void runRedis(){
        Match match = new Match("1", LocalDateTime.now(), "1,1,1","0,0,0");
        //레디스는 영속성 컨텍스트를 만들지 않음
        service.saveMatch(match);
        Match match1 = service.findMatchById("1");
        assertEquals(match.getDepartCoordinate(),match1.getDepartCoordinate());
        assertEquals(match.getDepartTime(),match1.getDepartTime());
    }

}
