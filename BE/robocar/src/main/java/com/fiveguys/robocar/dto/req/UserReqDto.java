package com.fiveguys.robocar.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserReqDto {
    @NotNull
    private String loginId;
    @NotNull
    private String password;
    @NotNull
    private String nickname;
}
