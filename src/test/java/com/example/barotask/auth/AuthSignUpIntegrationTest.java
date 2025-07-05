package com.example.barotask.auth;

import com.example.barotask.domain.auth.dto.request.AuthSignUpRequest;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AuthSignUpIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입(성공) - 성공")
    void signUp_success() throws Exception {

        AuthSignUpRequest request = AuthSignUpRequest.builder()
                .email("example@example.com")
                .nickname("example")
                .password("password1234")
                .passwordCheck("password1234")
                .userRole("ROLE_USER")
                .build();

        // when
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("example@example.com"))
                .andExpect(jsonPath("$.nickname").value("example"))
                .andExpect(jsonPath("$.userRole").value("ROLE_USER"));

        // then
        assertThat(userRepository.findByEmail("example@example.com")).isPresent();
    }

    @Test
    @DisplayName("회원가입(실패) - 이메일 미작성")
    void signUp_fail_whenEmailIsBlank() throws Exception {

        AuthSignUpRequest request = AuthSignUpRequest.builder()
                .nickname("example")
                .password("password1234")
                .passwordCheck("password1234")
                .userRole("ROLE_USER")
                .build();

        // when
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.error.message").value("이메일을 입력해주세요."));
    }

    @Test
    @DisplayName("회원가입(실패) - 닉네임 미작성")
    void signUp_fail_whenNicknameIsBlank() throws Exception {

        AuthSignUpRequest request = AuthSignUpRequest.builder()
                .email("xample@example.com")
                .password("password1234")
                .passwordCheck("password1234")
                .userRole("ROLE_USER")
                .build();

        // when
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.error.message").value("닉네임을 입력해주세요."));
    }

    @Test
    @DisplayName("회원가입(실패) - 비밀번호 미작성")
    void signUp_fail_whenPasswordIsBlank() throws Exception {

        AuthSignUpRequest request = AuthSignUpRequest.builder()
                .email("xample@example.com")
                .nickname("example")
                .passwordCheck("password1234")
                .userRole("ROLE_USER")
                .build();

        // when
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.error.message").value("비밀번호를 입력해주세요."));
    }

    @Test
    @DisplayName("회원가입(실패) - 비밀번호 확인 미작성")
    void signUp_fail_whenPasswordCheckIsBlank() throws Exception {

        AuthSignUpRequest request = AuthSignUpRequest.builder()
                .email("example@example.com")
                .nickname("example")
                .password("password1234")
                .userRole("ROLE_USER")
                .build();

        // when
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.error.message").value("비밀번호 확인을 입력해주세요."));
    }

    @Test
    @DisplayName("회원가입(실패) - 권한 미작성")
    void signUp_fail_whenUserRoleIsBlank() throws Exception {

        AuthSignUpRequest request = AuthSignUpRequest.builder()
                .email("example@example.com")
                .nickname("example")
                .password("password1234")
                .passwordCheck("password1234")
                .build();

        // when
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.error.message").value("권한을 입력해주세요."));
    }

    @Test
    @DisplayName("회원가입(실패) - 이메일 형식 올바르지 않음")
    void signUp_fail_whenEmailFormatIsInvalid() throws Exception {

        AuthSignUpRequest request = AuthSignUpRequest.builder()
                .email("example#example.com")
                .nickname("example")
                .password("password1234")
                .passwordCheck("password1234")
                .userRole("ROLE_USER")
                .build();

        // when
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.error.message").value("유효하지 않은 이메일 형식입니다."));
    }

    @Test
    @DisplayName("회원가입(실패) - 닉네임 형식 올바르지 않음")
    void signUp_fail_whenNicknameFormatIsInvalid() throws Exception {

        AuthSignUpRequest request = AuthSignUpRequest.builder()
                .email("example@example.com")
                .nickname("OverSizeNickname")
                .password("password1234")
                .passwordCheck("password1234")
                .userRole("ROLE_USER")
                .build();

        // when
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.error.message").value("유효하지 않은 닉네임 형식입니다."));
    }

    @Test
    @DisplayName("회원가입(실패) - 비밀번호 형식 올바르지 않음")
    void signUp_fail_whenPasswordFormatIsInvalid() throws Exception {

        AuthSignUpRequest request = AuthSignUpRequest.builder()
                .email("example@example.com")
                .nickname("example")
                .password("pass")
                .passwordCheck("pass")
                .userRole("ROLE_USER")
                .build();

        // when
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value("VALIDATION_FAILED"))
                .andExpect(jsonPath("$.error.message").value("유효하지 않은 비밀번호 형식입니다."));
    }

    @Test
    @DisplayName("회원가입(실패) - 비밀번호 확인 불일치 시 400 에러")
    void signUp_fail_whenPasswordMismatch() throws Exception {

        AuthSignUpRequest request = AuthSignUpRequest.builder()
                .email("example@example.com")
                .nickname("example")
                .password("password1234")
                .passwordCheck("wrong password1234")
                .userRole("ROLE_USER")
                .build();

        // when
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value("PASSWORD_MISMATCH"))
                .andExpect(jsonPath("$.error.message").value("비밀번호와 비밀번호 확인이 일치하지 않습니다."));
    }

    @Test
    @DisplayName("회원가입(실패) - 권한이 유효하지 않음")
    void signUp_fail_whenUserRoleIsInvalid() throws Exception {

        AuthSignUpRequest request = AuthSignUpRequest.builder()
                .email("example@example.com")
                .nickname("example")
                .password("password1234")
                .passwordCheck("password1234")
                .userRole("USER")
                .build();

        // when
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error.code").value("INVALID_ROLE"))
                .andExpect(jsonPath("$.error.message").value("유효하지 않은 권한입니다."));
    }

    @Test
    @DisplayName("회원가입(실패) - 중복 이메일 회원가입 시 400 에러")
    void signUp_duplicateEmail() throws Exception {
        // given
        AuthSignUpRequest request = AuthSignUpRequest.builder()
                .email("example@example.com")
                .nickname("example")
                .password("password1234")
                .passwordCheck("password1234")
                .userRole("ROLE_USER")
                .build();

        // 첫 가입
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("example@example.com"))
                .andExpect(jsonPath("$.nickname").value("example"))
                .andExpect(jsonPath("$.userRole").value("ROLE_USER"));;

        // 같은 이메일로 다시 가입
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value("DUPLICATE_EMAIL"))
                .andExpect(jsonPath("$.error.message").value("존재하는 이메일입니다."));
    }
}

