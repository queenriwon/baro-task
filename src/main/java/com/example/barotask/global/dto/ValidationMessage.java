package com.example.barotask.global.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationMessage {

    public static final String EMAIL_REQUIRED = "이메일을 입력해주세요.";
    public static final String NICKNAME_REQUIRED = "닉네임을 입력해주세요.";
    public static final String PASSWORD_REQUIRED = "비밀번호를 입력해주세요.";
    public static final String PASSWORD_CONFIRM_REQUIRED = "비밀번호 확인을 입력해주세요.";
    public static final String ROLE_REQUIRED = "권한을 입력해주세요.";

    public static final String INVALID_EMAIL = "유효하지 않은 이메일 형식입니다.";
    public static final String INVALID_NICKNAME = "유효하지 않은 닉네임 형식입니다.";
    public static final String INVALID_PASSWORD = "유효하지 않은 비밀번호 형식입니다.";

    public static final String PATTERN_PASSWORD_REGEXP = "^(?=.*\\d).{8,}$";


}
