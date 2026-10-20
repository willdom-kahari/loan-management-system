package io.github.willdomkahari.usermanagement;

import io.github.willdomkahari.usermanagement.model.Email;
import io.github.willdomkahari.usermanagement.model.Password;
import io.github.willdomkahari.usermanagement.model.Role;
import io.github.willdomkahari.usermanagement.model.UserId;
import io.github.willdomkahari.usermanagement.model.Username;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public final class User {
    private static final ZoneId ZONE_ID = ZoneId.of("Africa/Harare");
    private final UserId id;
    private final Username username;
    private final LocalDateTime createdAt;
    private Email email;
    private Password password;
    private Role role;
    private boolean enabled;
    private LocalDateTime updatedAt;
    private boolean defaultUser;

    private User(Username username, Email email, Password password, Role role) {
        LocalDateTime currentTime = LocalDateTime.now(ZONE_ID);
        this.id = UserId.newInstance();
        this.createdAt = currentTime;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.enabled = false;
        this.updatedAt = currentTime;
    }

    private User(UserId id, Username username, Email email, Password password, Role role, boolean enabled, boolean defaultUser, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
        this.updatedAt = updatedAt;
        this.defaultUser = defaultUser;

    }


    public static User from(String username, String email, String password, Role role) {
        return new User(Username.of(username), Email.of(email), Password.of(password), role);
    }

    static User admin(String username, String email, String password) {
        return from(username, email, password, Role.ADMIN);
    }

    static User manager(String username, String email, String password) {
        return from(username, email, password, Role.MANAGER);
    }

    static User loanOfficer(String username, String email, String password) {
        return from(username, email, password, Role.LOAN_OFFICER);
    }

    public static User from(UUID id, String username, String email, String password, String role, boolean enabled, boolean defaultUser, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new User(UserId.of(id), Username.of(username), Email.of(email), Password.of(password), Role.valueOf(role.toUpperCase()), enabled, defaultUser, createdAt, updatedAt);
    }

    void activate() {
        if (this.enabled){
            return;
        }
        this.enabled = true;
        this.updatedAt = LocalDateTime.now(ZONE_ID);
    }

    void deActivate() {
        this.enabled = false;
        this.updatedAt = LocalDateTime.now(ZONE_ID);
    }

    void changeRole(Role role) {
        if (role.equals(this.role)) {
            return;
        }
        this.role = role;
    }

    public UserId id() {
        return this.id;
    }

    public Username username() {
        return this.username;
    }

    public Email email() {
        return this.email;
    }

    public Password password() {
        return this.password;
    }

    public Role role() {
        return this.role;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isDefaultUser() {
        return this.defaultUser;
    }

    public LocalDateTime createdAt() {
        return this.createdAt;
    }

    public LocalDateTime updatedAt() {
        return this.updatedAt;
    }

    public void markDefault() {
        this.enabled = true;
        this.updatedAt = LocalDateTime.now(ZONE_ID);
        this.defaultUser = true;
    }
}
