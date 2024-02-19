package com.fiveguys.robocar.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class NaverMapConfig {

    public static final String DIRECTION_API_URL = "https://naveropenapi.apigw.ntruss.com/map-direction-15/v1/driving";
    public static final String GEOCODE_API_URL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";

    @Value("${naver.api.key.id}")
    private String apiKeyId;

    @Value("${naver.api.key.secret}")
    private String apiKeySecret;

}
