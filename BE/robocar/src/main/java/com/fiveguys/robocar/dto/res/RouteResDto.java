package com.fiveguys.robocar.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RouteResDto {
    private Long hostId;
    private Long guestId;
    private String carImage;
    private Long hostEstimatedArrivalTime;
    private Long guestEstimatedArrivalTime;
    private String carNumber;
    private String carName;
    private List<Node> hostNodes;
    private List<Node> guestNodes;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Node {
        private Double latitude;
        private Double longitude;
    }
}
