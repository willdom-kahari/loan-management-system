package io.github.willdomkahari.loanmanagement.user;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "default-user")
public record DefaultUserProperties(
        String username,
        String email,
        String password
) {
}
