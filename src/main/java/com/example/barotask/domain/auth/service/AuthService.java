package com.example.barotask.domain.auth.service;

import com.example.barotask.config.JwtUtil;
import com.example.barotask.config.PasswordEncoder;
import com.example.barotask.domain.auth.dto.request.AuthSignInRequest;
import com.example.barotask.domain.auth.dto.request.AuthSignUpRequest;
import com.example.barotask.domain.auth.dto.response.AuthTokenResponse;
import com.example.barotask.domain.user.dto.response.UsersResponse;
import com.example.barotask.domain.user.entity.Users;
import com.example.barotask.domain.user.service.UsersService;
import com.example.barotask.global.exception.BadRequestException;
import com.example.barotask.global.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.barotask.global.dto.ValidationMessage.INVALID_PASSWORD;
import static com.example.barotask.global.exception.ErrorMessage.PASSWORD_MISMATCH;
import static com.example.barotask.global.exception.ErrorMessage.PASSWORD_NOT_MATCHED;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /* 회원가입 */
    @Transactional
    public UsersResponse signUp(AuthSignUpRequest request) {

        if (!request.password().equals(request.passwordCheck())) {
            throw new BadRequestException(PASSWORD_MISMATCH);
        }

        Users users = usersService.saveUsers(request);

        return UsersResponse.of(users);
    }

    /* 로그인 */
    @Transactional(readOnly = true)
    public AuthTokenResponse signIn(AuthSignInRequest authSignInRequest) {

        Users users = usersService.findByEmailOrElseThrow(authSignInRequest.email());

        if (!passwordEncoder.matches(authSignInRequest.password(), users.getPassword())) {
            throw new UnauthorizedException(PASSWORD_NOT_MATCHED);
        }

        String token = jwtUtil.createAccessToken(users.getId(), users.getEmail(), users.getNickname(), users.getUserRole());

        return AuthTokenResponse.of(token);
    }
}
