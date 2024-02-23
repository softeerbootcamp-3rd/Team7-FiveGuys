package com.fiveguys.robocar.dto.res;

import com.fiveguys.robocar.entity.CarpoolRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
public class CarpoolListUpResDto {
    private final Long price;
    private List<CarpoolItem> list;

    public CarpoolListUpResDto(Long price) {
        this.price = price;
        this.list = new ArrayList<>();
    }

    @Builder
    @Getter
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
        private Long estimatedTime;
        private Long estimatedPrice;
    }


    public void addCarpoolItem(CarpoolItem carpoolItem){
        list.add(carpoolItem);
    }

    public void doSortByEstimatedTime(){
        list.sort(Comparator.comparingLong(CarpoolItem::getEstimatedTime));
    }

    public void trimByLengthOf(int maxListLength) {
        list = list.subList(0, Math.min(maxListLength,list.size()));
    }

}
