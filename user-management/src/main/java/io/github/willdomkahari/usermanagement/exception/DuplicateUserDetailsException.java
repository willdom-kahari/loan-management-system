package io.github.willdomkahari.usermanagement.exception;

public class DuplicateUserDetailsException extends RuntimeException {
    public DuplicateUserDetailsException(String message) {
        super(message);
    }
}
