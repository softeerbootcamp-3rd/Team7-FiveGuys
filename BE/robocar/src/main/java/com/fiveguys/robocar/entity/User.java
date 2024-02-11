package com.fiveguys.robocar.entity;

import com.fiveguys.robocar.models.Gender;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String loginId;


    private String password;


    private String phone;


    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String clientId;



}
