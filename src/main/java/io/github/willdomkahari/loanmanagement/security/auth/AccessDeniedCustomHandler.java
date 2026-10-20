package io.github.willdomkahari.loanmanagement.security.auth;

import io.github.willdomkahari.loanmanagement.api.ApiResponseWrapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AccessDeniedCustomHandler implements AccessDeniedHandler {
    private final JsonMapper jsonMapper;


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ApiResponseWrapper<Void> failed = ApiResponseWrapper.failed("You are forbidden to perform this action");
        response.getWriter().write(jsonMapper.writeValueAsString(failed));
    }
}
