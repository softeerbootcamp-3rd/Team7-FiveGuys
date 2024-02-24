package com.fiveguys.robocar.entity;


import com.fiveguys.robocar.entity.Car;
import com.fiveguys.robocar.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime departureTime;

    private String departureAddress;

    private String hostDestAddress;

    private String guestDestAddress;

    private LocalDateTime estimatedHostArrivalTime;

    private LocalDateTime estimatedGuestArrivalTime;


    private Long carId;

    private Long hostId;

    private Long guestId;


}
