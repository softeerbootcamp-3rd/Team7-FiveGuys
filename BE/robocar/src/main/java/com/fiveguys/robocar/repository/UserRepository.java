package com.fiveguys.robocar.repository;

import com.fiveguys.robocar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
