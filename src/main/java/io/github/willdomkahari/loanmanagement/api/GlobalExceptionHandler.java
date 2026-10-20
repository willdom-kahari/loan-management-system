package io.github.willdomkahari.loanmanagement.api;

import io.github.willdomkahari.usermanagement.exception.DuplicateUserDetailsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DuplicateUserDetailsException.class)
    public ResponseEntity<ApiResponseWrapper<Void>> handleDuplicates(DuplicateUserDetailsException ex){
        return new ResponseEntity<>(ApiResponseWrapper.failed(ex.getMessage()), HttpStatus.CONFLICT);
    }
}
