package com.fiveguys.robocar.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GarageReqDto {
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
}
