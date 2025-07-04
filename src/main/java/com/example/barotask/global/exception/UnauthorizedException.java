package com.example.barotask.global.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends HandledException {

    public UnauthorizedException(ErrorMessage errorMessage) {
        super(HttpStatus.UNAUTHORIZED, errorMessage);
    }
}
