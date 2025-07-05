package com.example.barotask.config;

import com.example.barotask.domain.user.entity.UserRole;
import com.example.barotask.global.annotation.AuthPermission;
import com.example.barotask.global.exception.ForbiddenException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.example.barotask.global.exception.ErrorMessage.ACCESS_DENIED;

@Component
@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        AuthPermission authPermission = handlerMethod.getMethodAnnotation(AuthPermission.class);

        if (authPermission == null) {
            return true; // 애너테이션 없으면 통과
        }

        String roleString = (String) request.getAttribute("userRole");
        UserRole userRole = UserRole.of(roleString);

        UserRole requiredRole = authPermission.role();

        if (userRole != requiredRole) {
            throw new ForbiddenException(ACCESS_DENIED);
        }

        return true;
    }
}
