package io.github.willdomkahari.usermanagement.exception;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException() {
        super("Invalid email format");
    }
}
