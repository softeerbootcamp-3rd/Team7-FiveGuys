package com.fiveguys.robocar.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonParserUtil {

    public static long extractDuration(String jsonResponse) {
        JsonObject responseObj = JsonParser.parseString(jsonResponse).getAsJsonObject();

        // 'route' -> 'trafast' 배열 -> 첫 번째 요소 -> 'summary' -> 'duration' 경로를 따라 값 추출
        long duration = responseObj.getAsJsonObject("route")
                .getAsJsonArray("trafast")
                .get(0).getAsJsonObject()
                .getAsJsonObject("summary")
                .get("duration").getAsLong();

        return duration;
    }
}
