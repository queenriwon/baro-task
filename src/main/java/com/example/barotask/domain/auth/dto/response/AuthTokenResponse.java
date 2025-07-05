package com.example.barotask.domain.auth.dto.response;

public record AuthTokenResponse(
        String token
) {
    public static AuthTokenResponse of(String token) {
        return new AuthTokenResponse(token);
    }
}
