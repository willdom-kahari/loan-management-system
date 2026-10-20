package io.github.willdomkahari.usermanagement.model;

import io.github.willdomkahari.usermanagement.util.Validators;

public record Username(String value) {
    public Username {
        Validators.notBlank(value, "username");
        value = value.toLowerCase()
                .trim();
    }

    public static Username of(String value) {
        return new Username(value);
    }
}
