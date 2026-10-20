package io.github.willdomkahari.loanmanagement.user;

import io.github.willdomkahari.usermanagement.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class SeedDefaultUser implements ApplicationRunner {
private final DefaultUserProperties properties;
private final UserService service;

    @Override
    public void run(ApplicationArguments args) {
        service.seedDefaultUser(properties.username(), properties.email(), properties.password());
    }
}
