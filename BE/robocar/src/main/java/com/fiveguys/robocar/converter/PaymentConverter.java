package com.fiveguys.robocar.converter;

import com.fiveguys.robocar.dto.req.PaymentReqDto;
import com.fiveguys.robocar.dto.res.PaymentResDto;
import com.fiveguys.robocar.entity.Payment;

import java.util.UUID;

public class PaymentConverter {

    public static Payment toPaymentEntity(PaymentReqDto paymentReqDto) {
        return Payment.builder()
                .payType(paymentReqDto.getPayType())
                .amount(paymentReqDto.getAmount())
                .orderName(paymentReqDto.getOrderName())
                .orderId(UUID.randomUUID().toString())
                .isPaySuccess(false)
                .build();
    }

    public static PaymentResDto toPaymentResDto(Payment payment) {
        return PaymentResDto.builder()
                .payType(payment.getPayType().getDescription())
                .amount(payment.getAmount())
                .orderId(payment.getOrderId())
                .orderName(payment.getOrderName())
                .customerId(payment.getUser().getId())
                .customerName(payment.getUser().getLoginId())
                .isCancel(payment.isCancel())
                .failReason(payment.getFailReason())
                .build();
    }

}
