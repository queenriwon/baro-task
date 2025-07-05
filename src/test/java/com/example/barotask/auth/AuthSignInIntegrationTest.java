package com.example.barotask.auth;

import com.example.barotask.config.PasswordEncoder;
import com.example.barotask.domain.auth.dto.request.AuthSignInRequest;

import com.example.barotask.domain.user.entity.UserRole;
import com.example.barotask.domain.user.entity.Users;
import com.example.barotask.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthSignInIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인(성공) - 성공")
    void signIn_success() throws Exception {

        // given
        String rawPassword = "password1234";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        Users savedUser = Users.of(
                "example@example.com",
                "nickname",
                encodedPassword,
                UserRole.ROLE_USER
        );
        userRepository.save(savedUser);

        // when
        AuthSignInRequest request = AuthSignInRequest.builder()
                .email("example@example.com")
                .password(rawPassword)
                .build();

        mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    @DisplayName("로그인(실패) - 존재하지 않는 이메일")
    void signIn_fail_whenEmailNotExists() throws Exception {

        // given
        AuthSignInRequest request = AuthSignInRequest.builder()
                .email("notfound@example.com")
                .password("password1234")
                .build();

        mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.code").value("EMAIL_NOT_FOUND"))
                .andExpect(jsonPath("$.error.message").value("존재하지 않는 이메일입니다."));
    }

    @Test
    @DisplayName("로그인(실패) - 비밀번호 불일치")
    void signIn_fail_whenPasswordDoesNotMatch() throws Exception {
        // given
        Users savedUser = Users.of(
                "example@example.com",
                "nickname",
                passwordEncoder.encode("correctPassword"),
                UserRole.ROLE_USER
        );
        userRepository.save(savedUser);

        // when: 틀린 비밀번호로 로그인 요청
        AuthSignInRequest request = AuthSignInRequest.builder()
                .email("example@example.com")
                .password("wrongPassword")
                .build();

        mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error.code").value("PASSWORD_NOT_MATCHED"))
                .andExpect(jsonPath("$.error.message").value("비밀번호가 일치하지 않습니다."));
    }
}
