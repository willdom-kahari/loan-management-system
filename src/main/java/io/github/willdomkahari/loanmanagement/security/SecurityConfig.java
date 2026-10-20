package io.github.willdomkahari.loanmanagement.security;

import io.github.willdomkahari.loanmanagement.security.auth.AccessDeniedCustomHandler;
import io.github.willdomkahari.loanmanagement.security.auth.AuthenticationEntryPointHandler;
import io.github.willdomkahari.loanmanagement.security.token.JwtConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
    private static final String[] WHITELIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/auth/**"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtConverter jwtCon, AuthenticationEntryPointHandler authHandler, AccessDeniedCustomHandler accessDeniedCustomHandler) {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req -> req
                                .requestMatchers(WHITELIST).permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(h -> h.jwt(
                                o -> o.jwtAuthenticationConverter(jwtCon))
                        .authenticationEntryPoint(authHandler)
                        .accessDeniedHandler(accessDeniedCustomHandler)
                )
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(UserDetailsService service, PasswordEncoder encoder) {
        var dao = new DaoAuthenticationProvider(service);
        dao.setPasswordEncoder(encoder);
        return new ProviderManager(dao);
    }

}
