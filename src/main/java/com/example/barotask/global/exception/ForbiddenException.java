package com.example.barotask.global.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends HandledException {

    public ForbiddenException(ErrorMessage errorMessage) {
        super(HttpStatus.FORBIDDEN, errorMessage);
    }
}
