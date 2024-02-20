package com.fiveguys.robocar.dto;

import com.fiveguys.robocar.util.JsonParserUtil.Coordinate;
import lombok.Getter;

import java.util.List;

@Getter
public class RouteInfo {
    private final long duration;
    private final long taxiFare;
    private final List<Coordinate> pathCoordinates;

    public RouteInfo(long duration, long taxiFare, List<Coordinate> pathCoordinates) {
        this.duration = duration;
        this.taxiFare = taxiFare;
        this.pathCoordinates = pathCoordinates;
    }
}
