package io.github.willdomkahari.loanmanagement.user;

import io.github.willdomkahari.usermanagement.User;
import io.github.willdomkahari.usermanagement.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {
    private final UserService service;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = service.findUserByUsername(username);
        AppUser appUser = AppUser.of(user);
        return new SecurityUser(appUser);
    }
}
