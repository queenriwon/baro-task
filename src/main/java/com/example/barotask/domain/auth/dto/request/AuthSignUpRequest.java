package com.example.barotask.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import static com.example.barotask.global.dto.ValidationMessage.*;

public record AuthSignUpRequest(

        @NotBlank(message = EMAIL_REQUIRED)
        @Email(message = INVALID_EMAIL)
        String email,

        @NotBlank(message = NICKNAME_REQUIRED)
        @Max(value = 8, message = INVALID_NICKNAME)
        String nickname,

        @NotBlank(message = PASSWORD_REQUIRED)
        @Pattern(regexp = PATTERN_PASSWORD_REGEXP, message = INVALID_PASSWORD)
        String password,

        @NotBlank(message = PASSWORD_CONFIRM_REQUIRED)
        String passwordCheck,

        @NotBlank(message = ROLE_REQUIRED)
        String userRole
) {
    @Builder
    public AuthSignUpRequest(String email, String nickname, String password, String passwordCheck, String userRole) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.userRole = userRole;
    }
}
