package io.github.willdomkahari.loanmanagement.security.adapter;


import io.github.willdomkahari.loanmanagement.user.AppUser;
import io.github.willdomkahari.loanmanagement.user.AppUserRepository;
import io.github.willdomkahari.usermanagement.User;
import io.github.willdomkahari.usermanagement.exception.UserNotFoundException;
import io.github.willdomkahari.usermanagement.model.Role;
import io.github.willdomkahari.usermanagement.model.Username;
import io.github.willdomkahari.usermanagement.ports.CurrentUser;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserAdapter implements CurrentUser {


    private final AppUserRepository appUserRepository;

    public CurrentUserAdapter(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public Role getRole() {
        return getUser().role();
    }

    @Override
    public User getUser() {
        String username =  getAuthentication().getName();
        AppUser appUser = appUserRepository.findAppUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return appUser.toUser();
    }

    @Override
    public Username getUsername() {
        return getUser().username();
    }

    private static @NonNull Authentication getAuthentication() {
        var securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
        Authentication authentication = securityContextHolderStrategy.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("No authenticated user in security context");
        }
        return authentication;
    }
}
