package com.fiveguys.robocar.service;

import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.config.TossPaymentConfig;
import com.fiveguys.robocar.converter.PaymentConverter;
import com.fiveguys.robocar.dto.PaymentSuccessDto;
import com.fiveguys.robocar.dto.req.PaymentReqDto;
import com.fiveguys.robocar.dto.res.PaymentResDto;
import com.fiveguys.robocar.entity.Payment;
import com.fiveguys.robocar.entity.User;
import com.fiveguys.robocar.models.TossPaymentConstants;
import com.fiveguys.robocar.repository.PaymentRepository;
import com.fiveguys.robocar.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

import static com.fiveguys.robocar.apiPayload.ResponseStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final TossPaymentConfig tossPaymentConfig;
    private final RestTemplate restTemplate;

    @Transactional
    public PaymentResDto requestTossPayment(Long userId, PaymentReqDto paymentReqDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseStatus.MEMBER_NOT_FOUND.getMessage()));

        Payment payment = PaymentConverter.toPaymentEntity(paymentReqDto);
        payment.setUser(user);

        PaymentResDto paymentResDto = PaymentConverter.toPaymentResDto(paymentRepository.save(payment));
        paymentResDto.setSuccessUrl(tossPaymentConfig.getSuccessUrl());
        paymentResDto.setFailUrl(tossPaymentConfig.getFailUrl());

        return paymentResDto;
    }

    @Transactional
    public void tossPaymentFail(String orderId, String message) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(() -> {
            throw new EntityNotFoundException(ResponseStatus.ORDER_NOT_FOUND.getMessage());
        });
        payment.setPaySuccess(false);
        payment.setFailReason(message);
    }

    @Transactional
    public PaymentSuccessDto tossPaymentSuccess(String paymentKey, String orderId, Long amount) throws JSONException {
        Payment payment = null;
        PaymentSuccessDto paymentSuccessDto = null;
        try {
            payment = verifyPayment(orderId, amount);
            paymentSuccessDto = acceptPaymentRequest(paymentKey, orderId, amount);
        } catch (EntityNotFoundException | IllegalArgumentException | IllegalStateException e) {
            throw e;
        }

        payment.setPaySuccessStatus(paymentKey, true);

        return paymentSuccessDto;
    }

    // 해당 결제 주문 내역과 요청 들어온 금액이 같은지 판별
    public Payment verifyPayment(String orderId, Long amount) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(() -> {
            throw new EntityNotFoundException(ResponseStatus.ORDER_NOT_FOUND.getMessage());
        });

        if (!payment.getAmount().equals(amount)) {
            throw new IllegalArgumentException(INVALID_PAYMENT_AMOUNT.getMessage());
        }

        return payment;
    }

    @Transactional
    public PaymentSuccessDto acceptPaymentRequest(String paymentKey, String orderId, Long amount) throws JSONException {
        HttpHeaders headers = setTossPaymentHeader();
        JSONObject params = setJSONParams(paymentKey, orderId, amount);

        PaymentSuccessDto result = null;
        try {
            HttpEntity<String> request = new HttpEntity<>(params.toString(), headers);
            result = restTemplate.postForObject(TossPaymentConfig.PAYMENT_ACCEPT_URL,
                    request,
                    PaymentSuccessDto.class);
        } catch (Exception e) {
            throw new IllegalStateException(UNPROCESSABLE_PAYMENT.getMessage());
        }
        return result;
    }


    @Transactional
    public Map cancelPaymentPoint(Long id, String paymentKey, String cancelReason, Long cancelAmount) throws JSONException {
        Payment payment = paymentRepository.findByPaymentKeyAndUser_Id(paymentKey, id).orElseThrow(() -> {
            throw new EntityNotFoundException(ORDER_NOT_FOUND.getMessage());
        });

        Long paymentAmount = payment.getAmount();

        // cancelAmount값이 0(파라미터로 환불금액이 안 들어온)인 경우 전체 취소로 간주
        if (cancelAmount == 0) {
            cancelAmount = paymentAmount;
        } else if (cancelAmount < 0 || paymentAmount < cancelAmount) {
            throw new IllegalArgumentException(NOT_CANCELABLE_AMOUNT.getMessage());
        }

        payment.setAmount(paymentAmount - cancelAmount);
        payment.setCancel(true);
        payment.setCancelReason(cancelReason);
        return tossPaymentCancel(paymentKey, cancelReason, cancelAmount);
    }

    public Map tossPaymentCancel(String paymentKey, String cancelReason, Long cancelAmount) throws JSONException {
        HttpHeaders headers = setTossPaymentHeader();
        JSONObject params = new JSONObject();
        params.put(TossPaymentConstants.CANCEL_REASON, cancelReason);
        params.put(TossPaymentConstants.CANCEL_AMOUNT, cancelAmount);

        return restTemplate.postForObject(TossPaymentConfig.URL + paymentKey + "/cancel",
                new HttpEntity<>(params.toString(), headers),
                Map.class);
    }
    private static JSONObject setJSONParams(String paymentKey, String orderId, Long amount) throws JSONException {
        JSONObject params = new JSONObject();
        params.put(TossPaymentConstants.PAYMENT_KEY, paymentKey);
        params.put(TossPaymentConstants.ORDER_ID, orderId);
        params.put(TossPaymentConstants.AMOUNT, amount);
        return params;
    }

    private HttpHeaders setTossPaymentHeader() {
        HttpHeaders headers = new HttpHeaders();
        String encodedAuthKey = new String(
                Base64.getEncoder().encode((tossPaymentConfig.getTestSecretKey() + ":").getBytes(StandardCharsets.UTF_8)));

        headers.setBasicAuth(encodedAuthKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

}
