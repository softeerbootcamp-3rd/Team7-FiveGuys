package com.fiveguys.robocar.entity;

import com.fiveguys.robocar.models.CarType;
import org.springframework.data.annotation.Id;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("CarpoolRequest")
@Getter
@AllArgsConstructor
@Builder
public class CarpoolRequest {
    @Id
    private Long id;
    private String hostNickname;

    private Double departLatitude;
    private Double departLongitude;

    private Double hostDestLatitude;
    private Double hostDestLongitude;

    private String hostDepartAddress;
    private String hostDestAddress;

    private Integer maleCount;
    private Integer femaleCount;

    private CarType carType;

}
