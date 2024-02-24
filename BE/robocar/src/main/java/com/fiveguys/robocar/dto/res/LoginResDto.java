package com.fiveguys.robocar.dto.res;

import lombok.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResDto {
    Long id;
    String nickname;
    String token;

}
