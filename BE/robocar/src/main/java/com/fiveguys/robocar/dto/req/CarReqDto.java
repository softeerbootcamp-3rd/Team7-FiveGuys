package com.fiveguys.robocar.dto.req;

import com.fiveguys.robocar.models.CarState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarReqDto {
    private Long garageId;
    private CarState state;
    private Integer seatTemperature;
    private Integer ventilationLevel;
    private Integer airConditionerTemperature;
    private boolean doorLock;
    private String carName;
    private String carImage;
    private String carNumber;
}

