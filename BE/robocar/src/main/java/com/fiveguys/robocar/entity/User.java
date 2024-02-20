package com.fiveguys.robocar.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String loginId;

    @Column(unique = true)
    private String nickname;

    private String password;

    private String clientToken;

    public void editNickname(String nickname){
        this.nickname = nickname;
    }

    public void editPassword(String password) {
        this.password = password;
    }
}
