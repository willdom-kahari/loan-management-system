package io.github.willdomkahari.loanmanagement.security.token;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JwtIssuer implements TokenIssuer {
    public final JwtProperties jwtProperties;

    private final JwtEncoder encoder;

    @Override
    public String issue(Authentication authentication) {
        var now = Instant.now(Clock.system(ZoneId.of("Africa/Harare")));
        final JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(authentication.getName())
                .issuer(jwtProperties.issuer())
                .claim(jwtProperties.authorityClaim(), resolveRole(authentication))
                .issuedAt(now)
                .expiresAt(now.plus(15, ChronoUnit.MINUTES))
                .build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private String resolveRole(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(Objects::nonNull)
                .map(a -> a.replaceFirst("^ROLE_", ""))
                .findFirst()
                .orElseThrow(() -> new AccessDeniedException("User has no assigned role"));
    }
}
