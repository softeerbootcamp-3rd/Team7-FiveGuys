package com.fiveguys.robocar.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TossPaymentConfig {

    public static final String URL = "https://api.tosspayments.com/v1/payments/";
    public static final String PAYMENT_ACCEPT_URL = "https://api.tosspayments.com/v1/payments/confirm";

    @Value("${payment.toss.test_client_api_key}")
    private String testClientKey;

    @Value("${payment.toss.test_secret_api_key}")
    private String testSecretKey;

    @Value("${payment.toss.success_url}")
    private String successUrl;

    @Value("${payment.toss.fail_url}")
    private String failUrl;

}
