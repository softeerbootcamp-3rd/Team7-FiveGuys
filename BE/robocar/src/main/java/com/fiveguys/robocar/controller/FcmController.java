package com.fiveguys.robocar.controller;

import com.fiveguys.robocar.apiPayload.ResponseApi;
import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.auth.Auth;
import com.fiveguys.robocar.dto.req.CarpoolRequestDTO;
import com.fiveguys.robocar.service.FirebaseCloudMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "FCM")
@RestController
@RequiredArgsConstructor
public class FcmController {
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @Operation(summary = "geust가 host에게 동승 요청을 보내는 push api")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "push 성공"),
            @ApiResponse(responseCode = "400", description = "해당하는 유저가 존재하지 않음")})
    @PostMapping("/push/carpool/request")
    public HttpEntity pushRequestMessage(@Auth Long guestId, @RequestBody CarpoolRequestDTO carpoolRequestDTO) throws JSONException {
        HttpEntity response = null;
        try {
            response = new HttpEntity<>(firebaseCloudMessageService.pushCarpoolRequest(guestId, carpoolRequestDTO));
        } catch (EntityNotFoundException e) {
            return ResponseApi.of(ResponseStatus.MEMBER_NOT_FOUND);
        }
        return ResponseApi.ok(response);
    }

    @Operation(summary = "호스트가 동승 수락 시 게스트에게 수락을 알려주기 위한 push api, AOS에서 직접 넣어주는 것이 아닌 '동승 수락 요청 API'의 서비스 로직의 일부로서 동작")
    @Parameter(name = "guestId", description = "동승 요청이 수락되었음을 알림 받을 guest의 id", example = "1")
    @Parameter(name = "inOperationId", description = "택시 경로 띄우기 위한 운행 내역 id", example = "17")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "push 성공"),
            @ApiResponse(responseCode = "400", description = "해당하는 유저가 존재하지 않음")})
    @PostMapping("/push/carpool/accept")
    public ResponseEntity pushAcceptMessage(@Auth Long hostId,
                                            @RequestParam Long guestId,
                                            @RequestParam Long inOperationId) throws JSONException {
        HttpEntity response = null;
        try {
            response = new HttpEntity<>(firebaseCloudMessageService.pushCarpoolAccept(guestId, inOperationId));
        } catch (EntityNotFoundException e) {
            return ResponseApi.of(ResponseStatus.MEMBER_NOT_FOUND);
        }
        return ResponseApi.ok(response);
    }

    @Operation(summary = "호스트가 동승 거절 시 게스트에게 거절을 알려주기 위한 push api")
    @Parameter(name = "guestId", description = "동승 요청이 거절됐음을 알림 받을 guest의 id", example = "3")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "push 성공"),
            @ApiResponse(responseCode = "400", description = "해당하는 유저가 존재하지 않음")})
    @PostMapping("/push/carpool/reject")
    public ResponseEntity pushRejectMessage(@Auth Long hostId, @RequestParam Long guestId) throws JSONException {
        HttpEntity response = null;
        try {
            response = new HttpEntity<>(firebaseCloudMessageService.pushCarpoolReject(guestId));
        } catch (EntityNotFoundException e) {
            return ResponseApi.of(ResponseStatus.MEMBER_NOT_FOUND);
        }
        return ResponseApi.ok(response);
    }

}
