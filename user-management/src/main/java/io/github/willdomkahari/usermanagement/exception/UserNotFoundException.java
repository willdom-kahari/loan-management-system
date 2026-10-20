package io.github.willdomkahari.usermanagement.exception;

public class UserNotFoundException extends RuntimeException {
    private static final String MESSAGE = "User %s not found";
    public UserNotFoundException(String username) {
        super(MESSAGE.formatted(username));
    }
}
