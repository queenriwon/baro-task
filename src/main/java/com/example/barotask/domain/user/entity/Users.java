package com.example.barotask.domain.user.entity;

import com.example.barotask.global.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String nickname;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Builder
    private Users(Long id, String email, String nickname, String password, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.userRole = userRole;
    }
}
