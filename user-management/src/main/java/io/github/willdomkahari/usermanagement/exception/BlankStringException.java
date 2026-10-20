package io.github.willdomkahari.usermanagement.exception;

public class BlankStringException extends RuntimeException {
    private static final String MESSAGE = "%s must not be blank";
    public BlankStringException(String name) {
        super(MESSAGE.formatted(name));
    }
}
