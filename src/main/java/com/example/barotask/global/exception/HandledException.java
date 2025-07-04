package com.example.barotask.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HandledException extends RuntimeException {

  private final HttpStatus httpStatus;
  private final ErrorMessage errorMessage;

  public HandledException(HttpStatus httpStatus, ErrorMessage errorMessage) {
      this.httpStatus = httpStatus;
      this.errorMessage = errorMessage;
  }
}
