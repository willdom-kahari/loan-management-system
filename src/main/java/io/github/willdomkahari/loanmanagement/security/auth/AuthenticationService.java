package io.github.willdomkahari.loanmanagement.security.auth;

import io.github.willdomkahari.loanmanagement.security.token.TokenIssuer;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenIssuer tokenIssuer;

    public AuthResponse authenticate(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        if (!authentication.isAuthenticated()) {
            throw new AccessDeniedException("Either username or password is not correct");
        }
        return new AuthResponse(tokenIssuer.issue(authentication));
    }

}
