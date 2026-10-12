package com.shieldpointcapital.lrms.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Shield Point Capital LRMS API", version = "v1"),
        security = @SecurityRequirement(name = "bearerAuth")   // apply the token to every endpoint
)
@SecurityScheme(
        name = "bearerAuth",             // the label used in the line above
        type = SecuritySchemeType.HTTP,  // plain HTTP auth
        scheme = "bearer",               // "Authorization: Bearer <token>"
        bearerFormat = "JWT"
)
public class OpenApiConfig {
}