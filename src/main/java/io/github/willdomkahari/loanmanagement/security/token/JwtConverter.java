package io.github.willdomkahari.loanmanagement.security.token;

import io.github.willdomkahari.usermanagement.User;
import io.github.willdomkahari.usermanagement.UserService;
import io.github.willdomkahari.usermanagement.exception.ActionNotAllowedException;
import io.github.willdomkahari.loanmanagement.api.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserService userService;
    private final JwtProperties jwtProperties;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        log.debug("Converting JWT to authentication token for subject: {}", jwt.getSubject());

        try {
            validateJwt(jwt);
            User user = findAndValidateUser(jwt);
            Collection<GrantedAuthority> authorities = buildAuthorities(jwt);
            return createAuthenticationToken(jwt, user, authorities);
        } catch (Exception e) {
            log.error("Error converting JWT to authentication token: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Validates the JWT token format and claims.
     *
     * @param jwt the JWT token to validate
     * @throws BusinessException if the token is invalid
     */
    private void validateJwt(Jwt jwt) {
        if (jwt == null) {
            throw new BusinessException("JWT token cannot be null");
        }

        String issuer = jwt.getClaimAsString(JwtClaimNames.ISS);
        if (!jwtProperties.issuer().equals(issuer)) {
            log.error("Invalid issuer: {}", issuer);
            throw new BusinessException("Invalid token issuer");
        }

        if (!jwt.hasClaim(jwtProperties.authorityClaim())) {
            throw new BusinessException("Token missing required roles claim");
        }
    }

    /**
     * Finds the user and validates their roles against the JWT claims.
     *
     * @param jwt the JWT token containing user information
     * @return the validated user
     */
    private User findAndValidateUser(Jwt jwt) {
        String username = jwt.getClaimAsString(JwtClaimNames.SUB);
        User user = userService.findUserByUsername(username);
        validateUserRoles(jwt, user);
        return user;
    }

    /**
     * Validates that the user's roles match those in the JWT token.
     *
     * @param jwt  the JWT token containing role claims
     * @param user the user whose roles should be validated
     * @throws BusinessException if the roles don't match
     */
    private void validateUserRoles(Jwt jwt, User user) {
        String tokenRole = jwt.getClaimAsString(jwtProperties.authorityClaim());
        String userRole = user.role().name();


        if (!Objects.equals(userRole, tokenRole)) {
            log.error("Role mismatch - Token role: {}, User role: {}", tokenRole, userRole);
            throw new ActionNotAllowedException("Token roles do not match user roles");
        }
    }

    /**
     * Builds the collection of granted authorities from the JWT roles claim.
     *
     * @param jwt the JWT token containing role information
     * @return collection of granted authorities
     */
    private Collection<GrantedAuthority> buildAuthorities(Jwt jwt) {
        List<String> roles = jwt.getClaimAsStringList(jwtProperties.authorityClaim());
        return roles.stream()
                .filter(Objects::nonNull)
                .map(this::createAuthority)
                .toList();
    }

    /**
     * Creates a GrantedAuthority from a role string, adding the ROLE_ prefix if
     * needed.
     *
     * @param role the role string
     * @return the granted authority
     */
    private GrantedAuthority createAuthority(String role) {
        String authorityString = role.startsWith(jwtProperties.rolePrefix()) ? role : jwtProperties.rolePrefix() + role;
        log.debug("Creating authority: {}", authorityString);
        return new SimpleGrantedAuthority(authorityString);
    }

    /**
     * Creates the final authentication token with enriched claims.
     *
     * @param jwt         the original JWT token
     * @param user        the validated user
     * @param authorities the granted authorities
     * @return the authentication token
     */
    private JwtAuthenticationToken createAuthenticationToken(Jwt jwt, User user,
                                                             Collection<GrantedAuthority> authorities) {
        Map<String, Object> enrichedClaims = new HashMap<>(jwt.getClaims());
        enrichedClaims.put("user", user);

        Jwt enrichedJwt = new Jwt(jwt.getTokenValue(),
                jwt.getIssuedAt(),
                jwt.getExpiresAt(),
                jwt.getHeaders(),
                enrichedClaims);

        return new JwtAuthenticationToken(enrichedJwt, authorities, Objects.requireNonNull(jwt.getSubject()));
    }
}
