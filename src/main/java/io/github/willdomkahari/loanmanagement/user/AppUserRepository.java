package io.github.willdomkahari.loanmanagement.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    boolean existsByRoleAndDefaultUser(String role, boolean defaultUser);

    boolean existsAppUserByUsernameOrEmail(String username, String email);

    Optional<AppUser> findAppUserByUsername(String username);
}