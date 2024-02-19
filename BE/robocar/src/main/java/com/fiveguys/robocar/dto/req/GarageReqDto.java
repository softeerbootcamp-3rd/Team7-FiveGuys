package com.fiveguys.robocar.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GarageReqDto {
    @NotNull
    @Schema(description = "위도")
    private Double latitude;
    @NotNull
    @Schema(description = "경도")
    private Double longitude;
}
