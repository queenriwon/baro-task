package com.example.barotask.global.exception;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    DUPLICATE_EMAIL("존재하는 이메일입니다."),
    PASSWORD_MISMATCH("비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    INVALID_ROLE("유효하지 않은 권한입니다."),

    EMAIL_NOT_FOUND("존재하지 않는 이메일입니다."),
    PASSWORD_NOT_MATCHED("비밀번호가 일치하지 않습니다."),

    ACCESS_DENIED("요청하신 작업을 수행할 권한이 없습니다."),
    USER_NOT_FOUND("존재하지 않는 사용자입니다."),
    REQUIRED_TOKEN("토큰이 필요합니다."),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN("사용이 만료된 토큰입니다."),

    INTERNAL_SERVER_ERROR("서버 오류가 발생하였습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
