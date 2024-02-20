package com.fiveguys.robocar.controller;

import com.fiveguys.robocar.apiPayload.ResponseApi;
import com.fiveguys.robocar.auth.Auth;
import com.fiveguys.robocar.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "JWT", description = "JWT 개발용 API")
public class JwtController {

    private final JwtUtil jwtUtil;

    @Operation(summary = "토큰 발급하기")
    @Parameter(name = "id", description = "토큰에 들어갈 userId, 존재하지 않는 userId로도 토큰이 생성되므로 주의")
    @GetMapping("/token")
    public ResponseEntity createToken(@RequestParam Long id) {
        String token = jwtUtil.createToken(id);
        return ResponseApi.ok(token);
    }

    @Operation(summary = "등록된 토큰에 userId값 확인하기", description = "권한이 필요한 API이므로 헤더에 유효한 Token이 존재해야 함")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰")})
    @GetMapping("/token/user-id")
    public ResponseEntity getUserIdInToken(@Auth Long userId) {
        return ResponseApi.ok(userId);
    }
}
