package com.fiveguys.robocar.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarpoolSuccessReqDto {
    @NotNull
    Long guestId;
    @NotBlank
    String guestDestAddress;
}
