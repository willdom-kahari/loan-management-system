package io.github.willdomkahari.loanmanagement.security.auth;

import io.github.willdomkahari.loanmanagement.api.ApiResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    private static final String BEARER_INVALID_TOKEN = "Bearer error=\"invalid_token\"";
    private static final String EXPIRED_MARKER = "expired";
    private static final String MSG_TOKEN_EXPIRED = "Your session has expired. Please log in again.";
    private static final String MSG_AUTH_FAILED = "Authentication failed. Either username or password is incorrect.";

    private final JsonMapper jsonMapper;

    @Override
    public void commence(@NonNull HttpServletRequest request,
                         @NonNull HttpServletResponse response,
                         @NonNull AuthenticationException ex) throws IOException {
        String message = isExpiredToken(ex) ? MSG_TOKEN_EXPIRED : MSG_AUTH_FAILED;
        writeUnauthorized(response, message);
    }

    private boolean isExpiredToken(AuthenticationException ex) {
        return ex.getCause() instanceof JwtValidationException jve
                && jve.getErrors().stream()
                .map(OAuth2Error::getDescription)
                .filter(Objects::nonNull)
                .anyMatch(description -> description.contains(EXPIRED_MARKER));
    }

    private void writeUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader(HttpHeaders.WWW_AUTHENTICATE, BEARER_INVALID_TOKEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        jsonMapper.writeValue(response.getWriter(), ApiResponseWrapper.failed(message));
    }
}
