package io.github.willdomkahari.usermanagement.exception;

public class ObjectNotNullException extends RuntimeException {
    private static final String MESSAGE = "%s must not be null";
    public ObjectNotNullException(String name) {
        super(MESSAGE.formatted(name));
    }
}
