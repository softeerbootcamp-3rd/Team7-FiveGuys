package com.fiveguys.robocar.service;

import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.config.FCMConfig;
import com.fiveguys.robocar.dto.req.CarpoolRequestDTO;
import com.fiveguys.robocar.entity.User;
import com.fiveguys.robocar.models.FcmNotificationType;
import com.fiveguys.robocar.repository.UserRepository;
import com.google.auth.oauth2.GoogleCredentials;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.fiveguys.robocar.models.FcmMessage.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@Slf4j
public class FirebaseCloudMessageService {

    private final FCMConfig fcmConfig;
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final HttpHeaders fcmHeader;

    @Autowired
    public FirebaseCloudMessageService(FCMConfig fcmConfig, RestTemplate restTemplate, UserRepository userRepository) throws IOException {
        this.fcmConfig = fcmConfig;
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
        fcmHeader = setDefaultFCMHeader();
    }

    public void pushCarpoolRequest(Long guestId, CarpoolRequestDTO carpoolRequestDTO) throws JSONException {
        User findUser = userRepository.findById(carpoolRequestDTO.getHostId())
                .orElseThrow(() -> new EntityNotFoundException(ResponseStatus.MEMBER_NOT_FOUND.getMessage()));

        JSONObject data = Data.of(carpoolRequestDTO.getHostId(), carpoolRequestDTO);
        JSONObject fcmMessage = createFCMMessage(findUser.getClientToken(), FcmNotificationType.CARPOOL_REQUEST, data);

        pushFcmMessage(fcmMessage);
    }

    public void pushCarpoolAccept(Long guestId, Long inOperationId) throws JSONException {
        User findUser = userRepository.findById(guestId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseStatus.MEMBER_NOT_FOUND.getMessage()));

        JSONObject fcmMessage = createFCMMessage(findUser.getClientToken(), FcmNotificationType.CARPOOL_ACCEPT, inOperationId);

        pushFcmMessage(fcmMessage);
    }

    public void pushCarpoolReject(Long guestId) throws JSONException {
        User findUser = userRepository.findById(guestId)
                .orElseThrow(() -> new EntityNotFoundException(ResponseStatus.MEMBER_NOT_FOUND.getMessage()));

        JSONObject fcmMessage = createFCMMessage(findUser.getClientToken(), FcmNotificationType.CARPOOL_REJECT, null);

        pushFcmMessage(fcmMessage);
    }

    private JSONObject createFCMMessage(String targetToken, FcmNotificationType fcmNotificationType, Object requestData) throws JSONException {
        JSONObject notification = Notification.of(fcmNotificationType.getTitle(), fcmNotificationType.getBody());
        JSONObject data;
        if (requestData instanceof Long) {
            data = Data.of((Long) requestData);
        } else if (requestData instanceof JSONObject) {
            data = (JSONObject) requestData;
        } else {
            data = new JSONObject();
        }
        JSONObject android = Android.of(fcmNotificationType.getClickAction());

        JSONObject message = Message.of(targetToken, notification, data, android);
        JSONObject fcmMessage = of(message);
        return fcmMessage;
    }

    private void pushFcmMessage(JSONObject message) {
        log.info("fcmMessage = {}", message.toString());
        HttpEntity<String> request = new HttpEntity<>(message.toString(), fcmHeader);
        String response = restTemplate.postForObject(fcmConfig.getMESSAGES_SEND_URL(), request, String.class);
        log.info("FCM response = {}", response);
    }

    private HttpHeaders setDefaultFCMHeader() throws IOException {
        HttpHeaders headers = new HttpHeaders();

        headers.setBearerAuth(getAccessToken());
        headers.setContentType(APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(APPLICATION_JSON));
        return headers;
    }


    private String getAccessToken() throws IOException {
        String firebaseConfigPath = fcmConfig.getFirebaseConfigPath();

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of(FCMConfig.GOOGLE_APIS_AUTH_URL));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}