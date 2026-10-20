package io.github.willdomkahari.usermanagement.exception;

public class PasswordMustBeBCryptException extends RuntimeException {

    public PasswordMustBeBCryptException() {
        super("Password must strictly be BCrypt");
    }
}
