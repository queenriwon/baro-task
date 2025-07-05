package com.example.barotask.config;

import com.example.barotask.global.exception.ForbiddenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.example.barotask.global.exception.ErrorMessage.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse,
            Object handler) throws Exception {

        String requestURI = httpRequest.getRequestURI();

        if (requestURI.startsWith("/auth") || requestURI.contains("/auth")) {
            return true;
        }

        if (requestURI.contains("/swagger-ui/") || requestURI.contains("/v3/api-docs")) {
            return true;
        }

        String bearerJwt = httpRequest.getHeader("Authorization");

        if (bearerJwt == null) {
            throw new ForbiddenException(REQUIRED_TOKEN);
        }

        String jwt = jwtUtil.substringToken(bearerJwt);

        try {
            Claims claims = jwtUtil.extractClaims(jwt);

            if (claims == null) {
                throw new ForbiddenException(INVALID_TOKEN);
            }

            httpRequest.setAttribute("userId", Long.parseLong(claims.getSubject()));
            httpRequest.setAttribute("email", claims.get("email"));
            httpRequest.setAttribute("nickname", claims.get("nickname"));
            httpRequest.setAttribute("userRole", claims.get("userRole"));

            return true;

        } catch (ExpiredJwtException e) {
            throw new ForbiddenException(EXPIRED_TOKEN);
        } catch (Exception e) {
            throw new ForbiddenException(INVALID_TOKEN);
        }
    }
}

