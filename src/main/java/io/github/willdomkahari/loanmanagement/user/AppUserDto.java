package io.github.willdomkahari.loanmanagement.user;

import io.github.willdomkahari.usermanagement.User;

import java.io.Serializable;

/**
 * DTO for {@link AppUser}
 */
public record AppUserDto(String username, String email, String role, boolean defaultUser) {
    public static AppUserDto of(User user){
        return new AppUserDto(user.username().value(), user.email().value(), user.role().name(), user.isDefaultUser());
    }
}