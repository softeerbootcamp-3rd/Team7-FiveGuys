package com.fiveguys.robocar.service;

import com.fiveguys.robocar.apiPayload.ResponseStatus;
import com.fiveguys.robocar.dto.req.UserCreateReqDto;
import com.fiveguys.robocar.dto.req.UserLoginReqDto;
import com.fiveguys.robocar.dto.req.UserNicknameReqDto;
import com.fiveguys.robocar.dto.req.UserPasswordReqDto;
import com.fiveguys.robocar.entity.User;
import com.fiveguys.robocar.repository.UserRepository;
import com.fiveguys.robocar.util.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
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

        User user = new User();
        user.setLoginId(loginId);
        user.setPassword(password);
        user.setNickname(nickname);

        userRepository.save(user);

    }

    @Transactional
    public void modifyNickname(UserNicknameReqDto userNicknameReqDto) {
        String nickname = userNicknameReqDto.getNickname();
        Long id = userNicknameReqDto.getId();

        User user = userRepository.findById(id).orElse(null);

        if(user == null)
            throw new EntityNotFoundException(ResponseStatus.USER_NOT_FOUND.getMessage());

        user.setNickname(nickname);

        userRepository.save(user);
    }

    @Transactional
    public void modifyPassword(UserPasswordReqDto userPasswordReqDto) {
        String password = userPasswordReqDto.getPassword();
        Long id = userPasswordReqDto.getId();

        User user = userRepository.findById(id).orElse(null);

        if(user == null)
            throw new EntityNotFoundException(ResponseStatus.USER_NOT_FOUND.getMessage());

        user.setPassword(password);
        userRepository.save(user);

    }

    @Transactional(readOnly = true)
    public boolean checkLoginId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    @Transactional(readOnly = true)
    public boolean checkNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public void userResign(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if(user == null)
            throw new EntityNotFoundException(ResponseStatus.USER_NOT_FOUND.getMessage());

        userRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public String userLogin(UserLoginReqDto userLoginReqDto) {
        System.out.println("2[["+userLoginReqDto.getLoginId()+userLoginReqDto.getPassword());
        String loginId = userLoginReqDto.getLoginId();
        String password = userLoginReqDto.getPassword();

        User user = userRepository.findByLoginId(loginId).orElse(null);
        if(user == null)
            throw new EntityNotFoundException(ResponseStatus.USER_NOT_FOUND.getMessage());
        else if(!user.getLoginId().equals(loginId) || !user.getPassword().equals(password))
            throw new EntityNotFoundException(ResponseStatus.USER_WRONG_PASSWORD.getMessage());
        return jwtUtil.createToken(user.getId());
    }
}
