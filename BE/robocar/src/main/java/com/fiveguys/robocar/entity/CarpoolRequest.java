package com.fiveguys.robocar.entity;

import org.springframework.data.annotation.Id;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash("CarpoolRequest")
@Getter
@AllArgsConstructor
@Builder
public class CarpoolRequest {
    @Id
    private Long hostId;
    private String hostNickname;
    private LocalDateTime departTime;

    private Double departLatitude;
    private Double departLongitude;

    private Double hostDestLatitude;
    private Double hostDestLongitude;

    private String hostDepartAddress;
    private String hostDestAddress;

    private Integer maleCount;
    private Integer femaleCount;

}
