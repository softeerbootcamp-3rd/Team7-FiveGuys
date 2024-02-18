package com.fiveguys.robocar.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CarpoolListUpResDto {
    private final Integer price;
    private final List<CarpoolItem> list;

    public CarpoolListUpResDto(int price) {
        this.price = price;
        this.list = new ArrayList<>();
    }

    @Builder
    public static class CarpoolItem {
        private Long hostId;
        private String hostNickname;
        private Double departLongitude;
        private Double departLatitude;
        private Double hostDestLongitude;
        private Double hostDestLatitude;
        private String hostDepartAddress;
        private String hostDestAddress;
        private Integer maleCount;
        private Integer femaleCount;
        private Integer estimatedTime; // 분 단위
        private Integer estimatedPrice;
    }

    public void addCarpoolItem(CarpoolItem carpoolItem){
        list.add(carpoolItem);
    }
}
