package com.example.barotask.global.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends HandledException {

    public BadRequestException(ErrorMessage errorMessage) {
        super(HttpStatus.BAD_REQUEST, errorMessage);
    }
}
