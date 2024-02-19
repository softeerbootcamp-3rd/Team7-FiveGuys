package com.fiveguys.robocar.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
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
}
