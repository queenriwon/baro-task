package com.example.barotask.domain.user.controller;

import com.example.barotask.domain.user.dto.response.UserResponse;
import com.example.barotask.domain.user.entity.UserRole;
import com.example.barotask.domain.user.service.UserService;
import com.example.barotask.global.annotation.AuthPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @AuthPermission(role = UserRole.ROLE_ADMIN)
    @PatchMapping("/{userId}/roles")
    public ResponseEntity<UserResponse> updateRole(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.updateRole(userId));
    }
}
