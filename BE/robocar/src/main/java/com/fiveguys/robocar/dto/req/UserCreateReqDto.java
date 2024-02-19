package com.fiveguys.robocar.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreateReqDto {
    @Size(max = 50)
    @NotBlank
    private String loginId;
    @Size(max = 50)
    @NotBlank
    private String password;
    @Size(max = 50)
    @NotBlank
    private String nickname;
}
