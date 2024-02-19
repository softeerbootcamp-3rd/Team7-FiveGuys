package com.fiveguys.robocar.dto.res;

import com.fiveguys.robocar.entity.CarpoolRequest;
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

    public void makeCarpoolList(Iterable<CarpoolRequest> iterableRequests){
        //TODO
        // 예상시간과 예상 금액 구하는 함수 필요
        CarpoolListUpResDto.CarpoolItem carpoolItem = null;
        for(CarpoolRequest req : iterableRequests){
            carpoolItem.builder()
                    .hostId(req.getHostId())
                    .hostNickname(req.getHostNickname())
                    .departLongitude(req.getDepartLongitude())
                    .departLatitude(req.getDepartLatitude())
                    .hostDestLongitude(req.getHostDestLongitude())
                    .hostDestLatitude(req.getHostDestLatitude())
                    .hostDepartAddress(req.getHostDepartAddress())
                    .hostDestAddress(req.getHostDestAddress())
                    .maleCount(req.getMaleCount())
                    .femaleCount(req.getFemaleCount())
                    .estimatedTime(111)
                    .estimatedPrice(111)
                    .build();
            this.addCarpoolItem(carpoolItem);
        }
    }
    private void addCarpoolItem(CarpoolItem carpoolItem){
        list.add(carpoolItem);
    }

    public void doSortByCoordinate(String guestDepartAddress, String guestDestAddress){
        //TODO
        // 정렬수행
    }
}
