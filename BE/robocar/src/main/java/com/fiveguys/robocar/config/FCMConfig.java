package com.fiveguys.robocar.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class FCMConfig {
    public final static String GOOGLE_APIS_AUTH_URL = "https://www.googleapis.com/auth/cloud-platform";

    @Value("${fcm.config.message_send_url}")
    private String MESSAGES_SEND_URL;

    @Value("${fcm.config.file_path}")
    private String firebaseConfigPath;

}
