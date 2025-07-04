package com.example.barotask.global.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends HandledException {

    public NotFoundException(ErrorMessage errorMessage) {
        super(HttpStatus.NOT_FOUND, errorMessage);
    }

}

