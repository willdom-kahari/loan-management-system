package io.github.willdomkahari.loanmanagement.user;

import io.github.willdomkahari.usermanagement.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_users_email", columnNames = "email"),
                @UniqueConstraint(name = "uk_users_username", columnNames = "username")
        }
)
public class AppUser {
    @Id
    private UUID id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private String role;
    private boolean enabled;
    private boolean defaultUser;
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static AppUser of(User user){
        return AppUser.builder()
                .id(user.id().value())
                .username(user.username().value())
                .email(user.email().value())
                .password(user.password().value())
                .role(user.role().name())
                .enabled(user.isEnabled())
                .defaultUser(user.isDefaultUser())
                .createdAt(user.createdAt())
                .updatedAt(user.updatedAt())
                .build();
    }

    public User toUser() {
        return User.from(id, username, email, password, role, enabled, defaultUser, createdAt, updatedAt);
    }
}
