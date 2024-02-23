package com.fiveguys.robocar.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fiveguys.robocar.models.CarState;
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

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garageId")
    private Garage garage;

    @Enumerated(EnumType.STRING)
    private CarState state;
    private Integer seatTemperature;
    private Integer ventilationLevel;
    private Integer airConditionerTemperature;
    private boolean doorLock;
    private String carName;
    private String carImage; // 차량 이미지 URL
    private String carNumber;

    public void edit(CarState state, Integer seatTemperature, Integer ventilationLevel,
                            Integer airConditionerTemperature, boolean doorLock, String carName,
                            String carImage, String carNumber) {
        this.state = state;
        this.seatTemperature = seatTemperature;
        this.ventilationLevel = ventilationLevel;
        this.airConditionerTemperature = airConditionerTemperature;
        this.doorLock = doorLock;
        this.carName = carName;
        this.carImage = carImage;
        this.carNumber = carNumber;
    }

}
