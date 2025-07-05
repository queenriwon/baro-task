package com.example.barotask.domain.user.dto.response;

import com.example.barotask.domain.user.entity.UserRole;
import com.example.barotask.domain.user.entity.Users;
import lombok.Builder;

public record UserResponse(
        String email,
        String nickname,
        UserRole userRole
) {
    @Builder
    public UserResponse {
    }

    public static UserResponse of(Users users) {
        return UserResponse.builder()
                .email(users.getEmail())
                .nickname(users.getNickname())
                .userRole(users.getUserRole())
                .build();
    }
}
