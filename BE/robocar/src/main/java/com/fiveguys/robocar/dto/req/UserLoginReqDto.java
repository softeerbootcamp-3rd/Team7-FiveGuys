package com.fiveguys.robocar.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginReqDto {
    @Size(max = 50)
    @NotBlank
    String loginId;

    @Size(max = 50)
    @NotBlank
    String password;

    @JsonProperty("FCMToken")
    @NotBlank
    String clientToken;
}
