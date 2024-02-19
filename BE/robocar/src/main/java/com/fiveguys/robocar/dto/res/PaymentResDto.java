package com.fiveguys.robocar.dto.res;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResDto {
    private Long amount;
    private String orderId;
    private String orderName;

    private String successUrl;
    private String failUrl;
}
