package com.fiveguys.robocar.entity;

import org.springframework.data.annotation.Id;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash("CarpoolRequest")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarpoolRequest {
    @Id
    private String hostId;
    private LocalDateTime departTime;

    private double departLatitude;
    private double departLongitude;

    private double hostDestLatitude;
    private double hostDestLongitude;
}
