package com.example.barotask.domain.auth.service;

import com.example.barotask.domain.auth.dto.request.AuthSignUpRequest;
import com.example.barotask.domain.auth.dto.response.UsersResponse;
import com.example.barotask.domain.user.entity.Users;
import com.example.barotask.domain.user.service.UsersService;
import com.example.barotask.global.exception.BadRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.barotask.global.exception.ErrorMessage.PASSWORD_MISMATCH;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersService usersService;

    @Transactional
    public UsersResponse signUp(AuthSignUpRequest request) {

        if (!request.password().equals(request.passwordCheck())) {
            throw new BadRequestException(PASSWORD_MISMATCH);
        }

        Users users = usersService.saveUsers(request);

        return UsersResponse.of(users);
    }
}
