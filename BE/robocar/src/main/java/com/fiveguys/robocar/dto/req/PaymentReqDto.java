package com.fiveguys.robocar.dto.req;

import com.fiveguys.robocar.models.PayType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentReqDto {
    @NotNull
    @Schema(description = "결제 타입", example = "CARD")
    private PayType payType;
    @NotNull
    @Schema(description = "결제 금액", example = "8500")
    private Long amount;
    @NotNull
    @Schema(description = "주문명", example = "동승")
    private String orderName;

}

