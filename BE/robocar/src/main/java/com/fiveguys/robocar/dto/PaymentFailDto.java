package com.fiveguys.robocar.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentFailDto {
    String errorCode;
    String errorMessage;
    String orderId;
}
