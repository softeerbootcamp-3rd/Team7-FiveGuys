package com.fiveguys.robocar.service;

import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.dto.req.UserCreateReqDto;
import com.fiveguys.robocar.dto.req.UserNicknameReqDto;
import com.fiveguys.robocar.dto.req.UserPasswordReqDto;
import com.fiveguys.robocar.entity.User;
import com.fiveguys.robocar.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {


    private final UserRepository userRepository;
    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Transactional
    public void createUser(UserCreateReqDto userCreateReqDto) {
        String loginId = userCreateReqDto.getLoginId();
        String password = userCreateReqDto.getPassword();
        String nickname = userCreateReqDto.getNickname();

        User user = new User();
        user.setLoginId(loginId);
        user.setPassword(password);
        user.setNickname(nickname);

        // 유저 테이블에서 loginId와
        userRepository.save(user);

    }

    @Transactional
    public void modifyNickname(UserNicknameReqDto userNicknameReqDto) {
        String nickname = userNicknameReqDto.getNickname();
        Long userId = userNicknameReqDto.getUserId();

        User user = userRepository.findById(userId).orElse(null);

        if(user == null)
            throw new EntityNotFoundException(ResponseStatus.USER_NOT_FOUND.getMessage());

        user.setNickname(nickname);
        //nickname는 유니크이므로 중복되는 경우 예외 발생
        userRepository.save(user);
    }

    @Transactional
    public void modifyPassword(UserPasswordReqDto userPasswordReqDto) {
        String password = userPasswordReqDto.getPassword();
        Long userId = userPasswordReqDto.getUserId();

        User user = userRepository.findById(userId).orElse(null);

        if(user == null)
            throw new EntityNotFoundException(ResponseStatus.USER_NOT_FOUND.getMessage());

        user.setPassword(password);
        userRepository.save(user);

    }

    @Transactional(readOnly = true)
    public boolean checkLoginId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }
}
