package com.fiveguys.robocar.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RouteService {

    private Gson gson = new Gson();

    public String extractPathCoordinatesAndRespond(String jsonResponse) {
        JsonObject responseObject = gson.fromJson(jsonResponse, JsonObject.class);
        JsonArray pathArray = responseObject.getAsJsonObject("route").getAsJsonArray("trafast").get(0).getAsJsonObject().getAsJsonArray("path");

        // pathArray를 List<Coordinate>로 변환
        Type listType = new TypeToken<ArrayList<Coordinate>>(){}.getType();
        List<Coordinate> pathCoordinates = gson.fromJson(pathArray, listType);

        // List<Coordinate>를 JSON 문자열로 변환하여 반환
        String response = gson.toJson(pathCoordinates);
        return response;
    }

    class Coordinate {
        double lat;
        double lng;
    }
}
