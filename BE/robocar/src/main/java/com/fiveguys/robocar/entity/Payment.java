package com.fiveguys.robocar.entity;

import com.fiveguys.robocar.models.PayType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PayType payType;
    private Long amount;

    private String paymentKey;
    private String orderId;
    //todo in_operation 도입 시 ENUM(혼자/동승) 어떤 주문인지에 대해 이름 부여
    private String orderName;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isPaySuccess;
    private String failReason;
    private boolean isCancel;
    private String cancelReason;

    public void setPaySuccessStatus(String paymentKey, boolean isPaySuccess) {
        this.paymentKey = paymentKey;
        this.isPaySuccess = isPaySuccess;
    }
}
