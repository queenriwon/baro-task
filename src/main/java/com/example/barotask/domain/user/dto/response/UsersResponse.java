package com.example.barotask.domain.user.dto.response;

import com.example.barotask.domain.user.entity.UserRole;
import com.example.barotask.domain.user.entity.Users;
import lombok.Builder;

public record UsersResponse(
        String email,
        String nickname,
        UserRole userRole
) {
    @Builder
    public UsersResponse {
    }

    public static UsersResponse of(Users users) {
        return UsersResponse.builder()
                .email(users.getEmail())
                .nickname(users.getNickname())
                .userRole(users.getUserRole())
                .build();
    }
}
