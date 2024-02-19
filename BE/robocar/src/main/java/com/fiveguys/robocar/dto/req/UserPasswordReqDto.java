package com.fiveguys.robocar.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPasswordReqDto {
    @NotNull
    Long id;
    @Size(max = 50)
    @NotBlank
    String password;
}