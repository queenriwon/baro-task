package com.example.barotask.global.dto;

import com.example.barotask.global.exception.ErrorMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private final Error error;

    private ErrorResponse(String errorCode, String message) {
        this.error = new Error(errorCode, message);
    }

    public static ErrorResponse of(ErrorMessage message) {
        return new ErrorResponse(message.name(), message.getMessage());
    }

    public static ErrorResponse of(String validationFailed, String errorMessage) {
        return new ErrorResponse(validationFailed, errorMessage);
    }

    @Getter
    public static class Error {
        private final String code;
        private final String message;

        public Error(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}

