package com.fiveguys.robocar.entity;

import org.springframework.data.annotation.Id;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash("Match")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Match {
    @Id
    private String hostId;
    private LocalDateTime departTime;
    private String departCoordinate;
    private String hostDestCoordinate;
}
