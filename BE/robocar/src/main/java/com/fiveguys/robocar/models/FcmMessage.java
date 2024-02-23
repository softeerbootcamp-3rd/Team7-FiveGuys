package com.fiveguys.robocar.models;

import com.fiveguys.robocar.dto.req.CarpoolRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessage {
    private boolean validateOnly;
    private Message message;

    public static JSONObject of(JSONObject message) throws JSONException {
        return new JSONObject().put("message", message);
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {
        private String token;
        private Notification notification;
        private Map data;
        private Android android;

        public static JSONObject of(String token, JSONObject notification, JSONObject android) throws JSONException {
            return new JSONObject()
                    .put("token", token)
                    .put("notification", notification)
                    .put("android", android);
        }
        public static JSONObject of(String token, JSONObject notification, JSONObject data, JSONObject android) throws JSONException {
            return new JSONObject()
                    .put("token", token)
                    .put("notification", notification)
                    .put("data", data)
                    .put("android", android);
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Notification {
        private String title;
        private String body;

        public static JSONObject of(String title, String body) throws JSONException {
            return new JSONObject().put("title", title).put("body", body);
        }
    }

    @AllArgsConstructor
    @Getter
    public static class Data {
        public static JSONObject of(Long id) throws JSONException {
            return new JSONObject().put("in_operation_id", String.valueOf(id));
        }

        public static JSONObject of(Long guestId, String guestNickname, CarpoolRequestDto carpoolRequestDTO) throws JSONException {
            return new JSONObject()
                    .put("guestId", String.valueOf(guestId))
                    .put("guestNickname", guestNickname)
                    .put("guestDestAddress", carpoolRequestDTO.getGuestDestAddress())
                    .put("maleCount", String.valueOf(carpoolRequestDTO.getMaleCount()))
                    .put("femaleCount", String.valueOf(carpoolRequestDTO.getFemaleCount()));
        }
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Android {
        private Notification notification;

        @Builder
        @AllArgsConstructor
        @Getter
        public static class Notification {
            private String click_action;
        }

        public static JSONObject of(String clickAction) throws JSONException {
            return new JSONObject()
                    .put("notification", new JSONObject().put("clickAction", clickAction));
        }
    }

}