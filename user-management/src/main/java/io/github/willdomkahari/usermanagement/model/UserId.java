package io.github.willdomkahari.usermanagement.model;

import io.github.willdomkahari.usermanagement.util.Validators;

import java.util.UUID;

public record UserId(UUID value) {
    public UserId {
        Validators.notNull(value, "userId");
    }

    public static UserId of(String value){
        Validators.notBlank(value, "userId");
        return new UserId(UUID.fromString(value));
    }

    public static UserId of(UUID value){
        return new UserId(value);
    }

    public static UserId newInstance(){
        return new UserId(UUID.randomUUID());
    }
}
