package com.example.barotask.domain.auth.controller;

import com.example.barotask.domain.auth.dto.request.AuthSignUpRequest;
import com.example.barotask.domain.auth.dto.response.UsersResponse;
import com.example.barotask.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /* 회원가입 */
    @PostMapping("/sign-up")
    public ResponseEntity<UsersResponse> signUp (
            @Valid @RequestBody AuthSignUpRequest authSignUpRequest
    ) {
        return ResponseEntity.ok(authService.signUp(authSignUpRequest));
    }
}
