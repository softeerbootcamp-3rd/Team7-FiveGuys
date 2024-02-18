package com.fiveguys.robocar.dto.res;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResDto {
    private String payType;
    private Long amount;
    private String orderId;
    private String orderName;
    private Long customerId;
    private String customerName;

    private String successUrl;
    private String failUrl;

    private String failReason;
    private boolean isCancel;
    private String cancelReason;
    private String createdAt;
}
