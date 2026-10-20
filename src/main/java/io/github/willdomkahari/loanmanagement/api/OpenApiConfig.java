package io.github.willdomkahari.loanmanagement.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

/**
 * Configuration class for OpenAPI documentation.
 * This class defines the global settings for the API documentation including
 * title, description, contact information, and security requirements.
 */
@OpenAPIDefinition(
    info = @Info(
        title = "Loan Management System API",
        version = "1.0",
        description = "This API provides endpoints for BancABC agency banking operations including bill payments, " +
                "remittances, account management, and administrative functions. The API is secured using JWT " +
                "authentication and requires appropriate roles for accessing different endpoints.",
        contact = @Contact(
            name = "Willdom Kahari",
            email = "developer.wadu@gmail.com"
        )
    ),
    security = {
        @SecurityRequirement(name = "Bearer Authentication")
    }
)
@SecurityScheme(
    name = "Bearer Authentication",
    description = "JWT Authentication. Enter your token in the format: Bearer <token>",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
