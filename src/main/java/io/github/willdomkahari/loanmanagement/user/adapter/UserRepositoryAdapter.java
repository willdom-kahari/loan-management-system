package io.github.willdomkahari.loanmanagement.user.adapter;



import io.github.willdomkahari.loanmanagement.user.AppUser;
import io.github.willdomkahari.loanmanagement.user.AppUserRepository;
import io.github.willdomkahari.usermanagement.User;
import io.github.willdomkahari.usermanagement.model.Role;
import io.github.willdomkahari.usermanagement.model.Username;
import io.github.willdomkahari.usermanagement.ports.UserRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final AppUserRepository repository;

    public UserRepositoryAdapter(AppUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(User user) {
        AppUser appUser = AppUser.of(user);
        repository.save(appUser);
    }

    @Override
    public boolean existsAsDefaultUser() {
        return repository.existsByRoleAndDefaultUser(Role.ADMIN.name(), true);
    }

    @Override
    public Optional<User> findUserByUsername(Username username) {
        Optional<AppUser> optionalAppUser = repository.findAppUserByUsername(username.value());
        if (optionalAppUser.isEmpty()) {
            return Optional.empty();
        }
        AppUser appUser = optionalAppUser.get();
        return Optional.ofNullable(appUser.toUser());
    }

    @Override
    public boolean isUserUnique(String username, String email) {
        return repository.existsAppUserByUsernameOrEmail(username, email);
    }
}
