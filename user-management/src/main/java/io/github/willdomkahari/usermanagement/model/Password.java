package io.github.willdomkahari.usermanagement.model;

import io.github.willdomkahari.usermanagement.util.Validators;

public record Password(String value) {
    public Password {
        value = value.trim();
        Validators.isBCrypt(value);
    }

    public static Password of(String value) {
        return new Password(value);
    }
}
