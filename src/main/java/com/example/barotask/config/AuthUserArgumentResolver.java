package com.example.barotask.config;

import com.example.barotask.domain.user.entity.UserRole;
import com.example.barotask.global.annotation.AuthUser;
import com.example.barotask.global.dto.AuthUserDto;
import com.example.barotask.global.exception.ErrorMessage;
import com.example.barotask.global.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Objects;

@Configuration
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthUser.class)
                && parameter.getParameterType().equals(AuthUserDto.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        Long userId = (Long) Objects.requireNonNull(request).getAttribute("userId");
        String email = (String) request.getAttribute("email");
        String nickname = (String) request.getAttribute("nickname");
        UserRole userRole = UserRole.of((String) request.getAttribute("userRole"));

        if (userId == null || email == null || nickname == null || userRole == null) {
            throw new UnauthorizedException(ErrorMessage.INVALID_TOKEN);
        }

        return new AuthUserDto(userId, email, nickname, userRole);
    }
}