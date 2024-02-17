package com.fiveguys.robocar.repository;

import com.fiveguys.robocar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByLoginId(String loginId);
    boolean existsByNickname(String nickname);
    User deleteByUserId(Long userId);
}
