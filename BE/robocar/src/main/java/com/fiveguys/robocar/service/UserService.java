package com.fiveguys.robocar.service;

import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.dto.req.UserCreateReqDto;
import com.fiveguys.robocar.dto.req.UserLoginReqDto;
import com.fiveguys.robocar.dto.req.UserNicknameReqDto;
import com.fiveguys.robocar.dto.req.UserPasswordReqDto;
import com.fiveguys.robocar.dto.res.LoginResDto;
import com.fiveguys.robocar.entity.User;
import com.fiveguys.robocar.repository.UserRepository;
import com.fiveguys.robocar.util.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    UserService(UserRepository userRepository, JwtUtil jwtUtil){

        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;

    }

    @Transactional
    public void createUser(UserCreateReqDto userCreateReqDto) {
        String loginId = userCreateReqDto.getLoginId();
        String password = userCreateReqDto.getPassword();
        String nickname = userCreateReqDto.getNickname();

        User user = new User().builder()
                .loginId(loginId)
                .password(password)
                .nickname(nickname)
                .build();

        userRepository.save(user);

    }

    @Transactional
    public void modifyNickname(UserNicknameReqDto userNicknameReqDto, Long id) {
        String nickname = userNicknameReqDto.getNickname();

        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ResponseStatus.MEMBER_NOT_FOUND.getMessage()));

        user.editNickname(nickname);

        userRepository.save(user);
    }

    @Transactional
    public void modifyPassword(UserPasswordReqDto userPasswordReqDto, Long id) {
        String password = userPasswordReqDto.getPassword();

        User user = userRepository.findById(id).orElseThrow(()->new EntityNotFoundException(ResponseStatus.MEMBER_NOT_FOUND.getMessage()));

        user.editPassword(password);
        userRepository.save(user);

    }

    @Transactional(readOnly = true)
    public boolean checkLoginId(String loginId) {
        return !userRepository.existsByLoginId(loginId);
    }

    @Transactional(readOnly = true)
    public boolean checkNickname(String nickname) {
        return !userRepository.existsByNickname(nickname);
    }

    @Transactional
    public void userResign(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->new EntityNotFoundException(ResponseStatus.MEMBER_NOT_FOUND.getMessage()));

        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public LoginResDto userLogin(UserLoginReqDto userLoginReqDto) {
        String loginId = userLoginReqDto.getLoginId();
        String password = userLoginReqDto.getPassword();

        User user = userRepository.findByLoginId(loginId).orElseThrow(()->new EntityNotFoundException(ResponseStatus.MEMBER_NOT_FOUND.getMessage()));

        if(!user.getLoginId().equals(loginId) || !user.getPassword().equals(password))
            throw new EntityNotFoundException(ResponseStatus.USER_WRONG_PASSWORD.getMessage());
        String token = jwtUtil.createToken(user.getId());
        String nickname = user.getNickname();
        Long id = user.getId();

        return new LoginResDto(id, nickname,token);
    }
}
