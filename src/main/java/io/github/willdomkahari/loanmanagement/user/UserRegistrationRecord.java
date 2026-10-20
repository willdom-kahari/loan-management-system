package io.github.willdomkahari.loanmanagement.user;


import io.github.willdomkahari.usermanagement.model.Role;

public record UserRegistrationRecord(
        String username,
        String email,
        String password,
        Role role
) {
}
