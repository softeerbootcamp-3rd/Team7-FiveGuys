package com.fiveguys.robocar.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CarpoolDeleteReqDto {
    @NotNull
    Long id;
    @NotNull
    Long guestId;
    @NotBlank
    String guestDestAddress;
}
