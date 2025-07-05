package com.example.barotask.domain.user.controller;

import com.example.barotask.domain.user.dto.response.UserResponse;
import com.example.barotask.domain.user.entity.UserRole;
import com.example.barotask.domain.user.service.UserService;
import com.example.barotask.global.annotation.AuthPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "User", description = "유저 관련 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "유저 권한 수정", description = "유저의 권한을 관리자로 변경할 수 있습니다.")
    @AuthPermission(role = UserRole.ROLE_ADMIN)
    @PatchMapping("/{userId}/roles")
    public ResponseEntity<UserResponse> updateRole(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.updateRole(userId));
    }
}
