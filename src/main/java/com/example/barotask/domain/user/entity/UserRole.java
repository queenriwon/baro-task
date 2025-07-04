package com.example.barotask.domain.user.entity;

import com.example.barotask.global.exception.UnauthorizedException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;

import static com.example.barotask.global.exception.ErrorMessage.INVALID_ROLE;

@Getter
public enum UserRole implements GrantedAuthority {

    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String userRole;

    public static UserRole of(String role) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.getUserRole().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new UnauthorizedException(INVALID_ROLE));
    }

    UserRole(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public String getAuthority() {
        return userRole;
    }
}

