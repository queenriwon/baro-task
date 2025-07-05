package com.example.barotask.global.dto;

import com.example.barotask.domain.user.entity.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthUserDto {

    private final Long userId;
    private final String email;
    private final String nickname;
    private final UserRole userRole;

    @Builder
    public AuthUserDto(Long userId, String email, String nickname, UserRole role) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.userRole = role;
    }
}
