package com.example.barotask.domain.user.service;

import com.example.barotask.config.PasswordEncoder;
import com.example.barotask.domain.auth.dto.request.AuthSignUpRequest;
import com.example.barotask.domain.user.dto.response.UserResponse;
import com.example.barotask.domain.user.entity.UserRole;
import com.example.barotask.domain.user.entity.Users;
import com.example.barotask.domain.user.repository.UserRepository;
import com.example.barotask.global.exception.BadRequestException;
import com.example.barotask.global.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.barotask.global.exception.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /* 회원 저장 */
    @Transactional
    public Users saveUsers(AuthSignUpRequest request) {

        if (existsByEmail(request.email())) {
            throw new BadRequestException(DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        Users users = Users.of(request.email(), request.nickname(), encodedPassword, UserRole.of(request.userRole()));
        userRepository.save(users);

        return users;
    }

    /* 회원 권한 수정 */
    @Transactional
    public UserResponse updateRole(Long userId) {
        Users users = findByUserIdOrElseThrow(userId);

        users.updateRole(UserRole.ROLE_ADMIN);

        return UserResponse.of(users);
    }

    private boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Users findByEmailOrElseThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(EMAIL_NOT_FOUND));
    }

    public Users findByUserIdOrElseThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }
}
