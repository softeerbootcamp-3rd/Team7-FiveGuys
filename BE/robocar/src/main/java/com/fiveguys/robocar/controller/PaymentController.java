package com.fiveguys.robocar.controller;

import com.fiveguys.robocar.apiPayload.ResponseApi;
import com.fiveguys.robocar.dto.PaymentFailDto;
import com.fiveguys.robocar.dto.PaymentSuccessDto;
import com.fiveguys.robocar.dto.req.PaymentReqDto;
import com.fiveguys.robocar.dto.res.PaymentResDto;
import com.fiveguys.robocar.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.fiveguys.robocar.apiPayload.ResponseStatus.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Toss-Payments", description = "결제를 처리해주는 API")
public class PaymentController {
    private final PaymentService paymentService;

    @Operation(summary = "결제 요청")
    @Parameter(name = "id", description = "결제를 요청하는 유저의 id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "결제 요청 성공"),
            @ApiResponse(responseCode = "400", description = "필수 파라미터 누락")})
    @PostMapping("/toss-payment")
    public ResponseEntity requestTossPayment(@RequestParam(name = "id") Long id,
                                             @RequestBody @Validated PaymentReqDto paymentReqDto,
                                             Errors errors) {
        if (errors.hasErrors()) {
            return ResponseApi.invalidArguments();
        }

        PaymentResDto paymentResDto = paymentService.requestTossPayment(id, paymentReqDto);
        return ResponseApi.ok(paymentResDto);
    }

    @Operation(summary = "결제 승인", description = "결제 요청 성공 시 redicret 되는 경로, 필요 파라미터들에 대한 값이 쿼리로 넘어옴")
    @Parameter(name = "paymentKey", description = "결제를 식별하는 역할로, 중복되지 않는 고유한 값")
    @Parameter(name = "orderId", description = "주문한 결제를 식별하는 역할로, 중복되지 않는 고유한 값")
    @Parameter(name = "amount", description = "결제 금액")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "결제 요청 성공"),
            @ApiResponse(responseCode = "400", description = "결제 내역과 요청 금액 불일치 or 주문 내역이 존재하지 않음"),
            @ApiResponse(responseCode = "422", description = "토스 서버 측에서 처리 불가능한 요청")})
    @GetMapping("/toss-payment/success")
    public ResponseEntity tossPaymentSuccess(@RequestParam(name = "paymentKey") String paymentKey,
                                             @RequestParam(name = "orderId") String orderId,
                                             @RequestParam(name = "amount") Long amount) throws JSONException {
        PaymentSuccessDto result = null;
        try {
            result = paymentService.tossPaymentSuccess(paymentKey, orderId, amount);
        } catch (IllegalArgumentException e) {
            return ResponseApi.of(INVALID_PAYMENT_AMOUNT);
        } catch (EntityNotFoundException e) {
            return ResponseApi.of(ORDER_NOT_FOUND);
        } catch (IllegalStateException e) {
            return ResponseApi.of(UNPROCESSABLE_PAYMENT);
        }
        return ResponseApi.ok(result);
    }

    @Operation(summary = "결제 실패", description = "결제 요청 실패 시 redirect 되는 url")
    @Parameter(name = "code", description = "토스 측 에러 코드")
    @Parameter(name = "message", description = "토스 측 에러 메시지")
    @Parameter(name = "orderId", description = "실패한 주문 id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "결제 요청 실패"),
            @ApiResponse(responseCode = "400", description = "주문 내역이 존재하지 않음")})
    @GetMapping("/toss-payment/fail")
    public ResponseEntity tossPaymentFail(@RequestParam String code,
                                          @RequestParam String message,
                                          @RequestParam String orderId) {
        try {
            paymentService.tossPaymentFail(orderId, message);
        } catch (EntityNotFoundException e) {
            return ResponseApi.of(ORDER_NOT_FOUND);
        }
        return ResponseApi.ok(PaymentFailDto.builder()
                .errorCode(code)
                .errorMessage(message)
                .orderId(orderId)
                .build());
    }

    @Operation(summary = "결제 취소 및 환불", description = "환불할 금액에 대한 처리, cancelAmount에 값이 없거나 0이면 전체 환불(결제 취소)로 처리")
    @Parameter(name = "id", description = "결제를 취소하는 유저의 id")
    @Parameter(name = "paymentKey", description = "취소하려는 결제의 고유 Key")
    @Parameter(name = "cancelReason", description = "결제 취소 이유")
    @Parameter(name = "cancelAmount", description = "결제 취소 금액")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "결제 취소 성공"),
            @ApiResponse(responseCode = "400", description = "결제 요청 실패"),
            @ApiResponse(responseCode = "422", description = "결제 요청 실패"),
    })
    @PostMapping("/toss-payment/cancel")
    public ResponseEntity tossPaymentCancelPoint(@RequestParam Long id,
                                                 @RequestParam String paymentKey,
                                                 @RequestParam String cancelReason,
                                                 @RequestParam(required = false, defaultValue = "0") Long cancelAmount) throws JSONException {
        Map data = null;
        try {
            data = paymentService.cancelPaymentPoint(id, paymentKey, cancelReason, cancelAmount);
        } catch (EntityNotFoundException e) {
            return ResponseApi.of(ORDER_NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return ResponseApi.of(NOT_CANCELABLE_AMOUNT);
        }

        return ResponseApi.ok(data);
    }

}
