package com.fiveguys.robocar.entity;


import com.fiveguys.robocar.entity.Car;
import com.fiveguys.robocar.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime departureTime;

    private String departureAddress;

    private String departureCoordinate;

    private String hostDestCoordinate;

    private String guestDestCoordinate;

    private LocalDateTime estimatedHostArrivalTime;

    private LocalDateTime estimatedGuestArrivalTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carId")
    private Car car;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hostId")
    private User host;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guestId")
    private User guest;


}
