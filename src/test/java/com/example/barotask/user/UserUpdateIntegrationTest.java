package com.example.barotask.user;

import com.example.barotask.config.JwtUtil;
import com.example.barotask.config.PasswordEncoder;
import com.example.barotask.domain.user.entity.UserRole;
import com.example.barotask.domain.user.entity.Users;
import com.example.barotask.domain.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserUpdateIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    private Users admin;
    private Users user;

    @BeforeEach
    void setUp() {
        admin = userRepository.save(
                Users.of("admin@email.com", "admin", passwordEncoder.encode("admin123"), UserRole.ROLE_ADMIN)
        );

        user = userRepository.save(
                Users.of("user@email.com", "user", passwordEncoder.encode("user123"), UserRole.ROLE_USER)
        );
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("권한 변경(성공) - 관리자 토큰으로 다른 유저 권한 변경")
    void changeRole_success_withAdminToken() throws Exception {
        String adminToken = jwtUtil.createAccessToken(admin.getId(), admin.getEmail(), admin.getNickname(), admin.getUserRole());

        mockMvc.perform(patch("/users/{id}/roles", user.getId())
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user@email.com"))
                .andExpect(jsonPath("$.nickname").value("user"))
                .andExpect(jsonPath("$.userRole").value("ROLE_ADMIN"));
    }

    @Test
    @DisplayName("권한 변경(실패) - 일반 사용자 토큰으로 권한 변경")
    void changeRole_fail_withUserToken() throws Exception {
        String userToken = jwtUtil.createAccessToken(user.getId(), user.getEmail(), user.getNickname(), user.getUserRole());

        mockMvc.perform(patch("/users/{id}/roles", user.getId())
                        .header("Authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error.code").value("ACCESS_DENIED"))
                .andExpect(jsonPath("$.error.message").value("요청하신 작업을 수행할 권한이 없습니다."));
    }

    @Test
    @DisplayName("권한 변경(실패) - 존재하지 않는 사용자")
    void changeRole_fail_whenUserNotExists() throws Exception {
        String adminToken = jwtUtil.createAccessToken(admin.getId(), admin.getEmail(), admin.getNickname(), admin.getUserRole());

        Long nonExistentId = 9999L;

        mockMvc.perform(patch("/users/{id}/roles", nonExistentId)
                        .header("Authorization", adminToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.code").value("USER_NOT_FOUND"))
                .andExpect(jsonPath("$.error.message").value("존재하지 않는 사용자입니다."));
    }

    @Test
    @DisplayName("권한 변경(실패) - 만료된 토큰")
    void changeRole_fail_withExpiredToken() throws Exception {

        String expiredToken = "expired.token.here";

        mockMvc.perform(patch("/users/{id}/roles", user.getId())
                        .header("Authorization", expiredToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
