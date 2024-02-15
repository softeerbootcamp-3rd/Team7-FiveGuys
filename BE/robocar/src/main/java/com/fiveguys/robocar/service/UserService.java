package com.fiveguys.robocar.service;

import com.fiveguys.robocar.dto.req.UserReqDto;
import com.fiveguys.robocar.entity.User;
import com.fiveguys.robocar.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {


    private final UserRepository userRepository;
    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Transactional
    public void createUser(UserReqDto userReqDto) {
        String loginId = userReqDto.getLoginId();
        String password = userReqDto.getPassword();
        String nickname = userReqDto.getNickname();

        User user = new User();
        user.setLoginId(loginId);
        user.setPassword(password);
        user.setNickname(nickname);

        // 유저 테이블에서 loginId와 nickname는 유니크이므로
        // 중복되는 경우 예외 발생
        userRepository.save(user);

    }
}
