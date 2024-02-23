package com.fiveguys.robocar.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CarpoolRequestDto {
    @Schema(description = "동승 요청 push 알림 받을 host의 주소", example = "1")
    private Long hostId;

    @Schema(description = "동승 수락 시 db에 저장하기 위한 guest의 도착 주소", example = "서울시 학동역 샬라샬라")
    private String guestDestAddress;

    @Schema(description = "호스트에게 동승 요청 정보를 보여주기 위함", example = "2")
    private Long maleCount;

    @Schema(description = "호스트에게 동승 요청 정보를 보여주기 위함", example = "1")
    private Long femaleCount;

}
