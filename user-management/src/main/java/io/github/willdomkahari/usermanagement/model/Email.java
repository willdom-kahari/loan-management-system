package io.github.willdomkahari.usermanagement.model;

import io.github.willdomkahari.usermanagement.util.Validators;

public record Email(String value) {

    public Email{
        Validators.isEmail(value);
        value = value.toLowerCase()
                .trim();
    }

    public static Email of(String value){
        return new Email(value);
    }
}
