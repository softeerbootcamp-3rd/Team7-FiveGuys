package com.fiveguys.robocar.dto.req;

import com.fiveguys.robocar.models.CarType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarpoolRegisterReqDto {
    @NotBlank
    String DepartAddress;
    @NotBlank
    String DestAddress;
    @NotNull
    Integer maleCount;
    @NotNull
    Integer femaleCount;
    @NotNull
    CarType carType;
}
