package io.github.willdomkahari.loanmanagement.security.token;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "token.jwt")
public record JwtProperties(String issuer, String authorityClaim, String rolePrefix, String secretKey) {
    public JwtProperties {
        issuer = issuer == null ? "self" : issuer;
        authorityClaim = authorityClaim == null ? "authority" : authorityClaim;
        rolePrefix = rolePrefix == null ? "ROLE_" : rolePrefix;
        secretKey = secretKey == null ? "B7X8uvKc8MQNSSdNarOq4ikSBYSxnQOq" : secretKey;
    }
}