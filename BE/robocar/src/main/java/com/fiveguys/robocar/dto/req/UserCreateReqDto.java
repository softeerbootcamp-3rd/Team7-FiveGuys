package com.fiveguys.robocar.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreateReqDto {
    @Size(max = 50)
    @NotBlank
    @Schema(description = "로그인아이디")
    private String loginId;
    @Size(max = 50)
    @NotBlank
    @Schema(description = "비밀번호")
    private String password;
    @Size(max = 50)
    @NotBlank
    @Schema(description = "닉네임")
    private String nickname;
}
