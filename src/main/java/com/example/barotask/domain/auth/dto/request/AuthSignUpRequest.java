package com.example.barotask.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;

import static com.example.barotask.global.dto.ValidationMessage.*;

@Schema(description = "회원가입을 위한 DTO")
public record AuthSignUpRequest(

        @Schema(example = "example@example.com")
        @NotBlank(message = EMAIL_REQUIRED)
        @Email(message = INVALID_EMAIL)
        String email,

        @Schema(example = "닉네임")
        @NotBlank(message = NICKNAME_REQUIRED)
        @Size(max = 8, message = INVALID_NICKNAME)
        String nickname,

        @Schema(example = "password1234")
        @NotBlank(message = PASSWORD_REQUIRED)
        @Pattern(regexp = PATTERN_PASSWORD_REGEXP, message = INVALID_PASSWORD)
        String password,

        @Schema(example = "password1234")
        @NotBlank(message = PASSWORD_CONFIRM_REQUIRED)
        String passwordCheck,

        @Schema(example = "ROLE_USER")
        @NotBlank(message = ROLE_REQUIRED)
        String userRole
) {
    @Builder
    public AuthSignUpRequest {
    }
}
