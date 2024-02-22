package com.fiveguys.robocar.models;

public enum FcmNotificationType {
    CARPOOL_REQUEST("동승 요청이 들어왔습니다", "수락/거절을 선택해주세요~", "CAR_POOL_REQUEST"),
    CARPOOL_ACCEPT("동승 요청이 수락되었습니다", "택시가 출발지점으로 오고 있어요. 지도를 확인해주세요.", "CAR_POOL_ACCEPT"),
    CARPOOL_REJECT("동승 요청이 거절되었습니다", "다른 동승을 선택해주세요.. ㅠㅠ", "CAR_POOL_REJECT");

    private final String title;
    private final String body;
    private final String clickAction;

    FcmNotificationType(String title, String body, String clickAction) {
        this.title = title;
        this.body = body;
        this.clickAction = clickAction;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getClickAction() {
        return clickAction;
    }
}

