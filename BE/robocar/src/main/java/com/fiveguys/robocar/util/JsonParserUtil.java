package com.fiveguys.robocar.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonParserUtil {

    private static final Gson gson = new Gson();

    public static long extractDuration(String jsonResponse) {
        JsonObject responseObj = JsonParser.parseString(jsonResponse).getAsJsonObject();
        long duration = responseObj.getAsJsonObject("route")
                .getAsJsonArray("trafast")
                .get(0).getAsJsonObject()
                .getAsJsonObject("summary")
                .get("duration").getAsLong();

        return duration;
    }

    public static long extractTaxiFare(String jsonResponse) {
        JsonObject responseObj = JsonParser.parseString(jsonResponse).getAsJsonObject();
        long taxiFare = responseObj.getAsJsonObject("route")
                .getAsJsonArray("trafast")
                .get(0).getAsJsonObject()
                .getAsJsonObject("summary")
                .get("taxiFare").getAsLong();

        return taxiFare;
    }

    public static List<Coordinate> extractPathCoordinates(String jsonResponse) {
        JsonObject responseObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonArray pathArray = responseObject.getAsJsonObject("route")
                .getAsJsonArray("trafast").get(0)
                .getAsJsonObject().getAsJsonArray("path");

        List<Coordinate> pathCoordinates = new ArrayList<>();
        for (JsonElement element : pathArray) {
            JsonArray coordinateArray = element.getAsJsonArray();
            double lat = coordinateArray.get(1).getAsDouble(); // JSON 배열에서 두 번째 요소가 위도
            double lng = coordinateArray.get(0).getAsDouble(); // JSON 배열에서 첫 번째 요소가 경도
            pathCoordinates.add(new Coordinate(lat, lng));
        }

        return pathCoordinates;
    }

    public static class Coordinate {
        private double latitude;
        private double longitude;

        public Coordinate(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}
