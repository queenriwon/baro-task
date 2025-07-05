package com.example.barotask.domain.user.service;

import com.example.barotask.config.PasswordEncoder;
import com.example.barotask.domain.auth.dto.request.AuthSignUpRequest;
import com.example.barotask.domain.user.entity.UserRole;
import com.example.barotask.domain.user.entity.Users;
import com.example.barotask.domain.user.repository.UsersRepository;
import com.example.barotask.global.exception.BadRequestException;
import com.example.barotask.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.barotask.global.exception.ErrorMessage.DUPLICATE_EMAIL;
import static com.example.barotask.global.exception.ErrorMessage.EMAIL_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public Users saveUsers(AuthSignUpRequest request) {

        if (existsByEmail(request.email())) {
            throw new BadRequestException(DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(request.password());

        Users users = Users.of(request.email(), request.nickname(), encodedPassword, UserRole.of(request.userRole()));
        usersRepository.save(users);

        return users;
    }

    private boolean existsByEmail(String email) {
        return usersRepository.existsByEmail(email);
    }

    public Users findByEmailOrElseThrow(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(EMAIL_NOT_FOUND));
    }
}
