package io.github.willdomkahari.loanmanagement.security.token;

import org.springframework.security.core.Authentication;

public interface TokenIssuer {
    String issue(Authentication authentication);
}