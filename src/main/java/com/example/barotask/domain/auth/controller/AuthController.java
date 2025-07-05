package com.example.barotask.domain.auth.controller;

import com.example.barotask.domain.auth.dto.request.AuthSignInRequest;
import com.example.barotask.domain.auth.dto.request.AuthSignUpRequest;
import com.example.barotask.domain.auth.dto.response.AuthTokenResponse;
import com.example.barotask.domain.user.dto.response.UserResponse;
import com.example.barotask.domain.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Auth", description = "회원가입 및 로그인과 관련된 API")
public class AuthController {

    private final AuthService authService;

    /* 회원가입 */
    @Operation(summary = "회원가입", description = "회원가입에 대한 API입니다.")
    @PostMapping("/sign-up")
    public ResponseEntity<UserResponse> signUp (
            @Valid @RequestBody AuthSignUpRequest authSignUpRequest
    ) {
        return ResponseEntity.ok(authService.signUp(authSignUpRequest));
    }

    /* 로그인 */
    @Operation(summary = "로그인", description = "로그인에 대한 API입니다.")
    @PostMapping("/sign-in")
    public ResponseEntity<AuthTokenResponse> signIn (
            @Valid @RequestBody AuthSignInRequest authSignInRequest
    ) {
        return ResponseEntity.ok(authService.signIn(authSignInRequest));
    }
}
