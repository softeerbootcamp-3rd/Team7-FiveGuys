package com.fiveguys.robocar.dto.res;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RouteSoloResDto {
    private String carImage;
    private Long EstimatedArrivalTime;
    private String carNumber;
    private String carName;
    private List<Node> nodes;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Node {
        private Double latitude;
        private Double longitude;
    }
}
