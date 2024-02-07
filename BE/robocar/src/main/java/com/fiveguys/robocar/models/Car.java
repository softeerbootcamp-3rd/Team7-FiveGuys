package com.fiveguys.robocar.models;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garageId")
    private Garage garage;

    @Enumerated(EnumType.STRING)
    private CarState state;
    private Integer seatTemperature;
    private Integer ventilationLevel;
    private Integer airConditionerTemperature;
    private boolean doorLock;



}
