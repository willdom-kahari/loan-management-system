package io.github.willdomkahari.usermanagement.ports;

import io.github.willdomkahari.usermanagement.User;
import io.github.willdomkahari.usermanagement.model.Username;

import java.util.Optional;

public interface UserRepositoryPort {
    void save(User user);

    boolean existsAsDefaultUser();

    Optional<User> findUserByUsername(Username username);

    boolean isUserUnique(String username, String email);
}
