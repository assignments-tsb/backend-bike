package com.bike.app.framework.openapi;

import io.micronaut.openapi.annotation.OpenAPIInclude;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(
                title = "Bike Rental Service API",
                version = "1.0",
                description = "a simple CRUD web application for a bike rental shop",
                license = @License(name = "Apache 2.0", url = "https://codefactory.dev"),
                contact = @Contact(url = "https://bikerental.com", name = "LM Bibera", email = "secret.transaction@gmail.com")
        ),
        security = @SecurityRequirement(name = "api", scopes = {"ADMIN", "STAFF"}),
        servers = {
                @Server(url = "http://localhost:8080", description = "Local Development Server"),
                @Server(url = "https://api.bike.com", description = "Production Server")
        }
)
@OpenAPIInclude(
        classes = {
                io.micronaut.security.endpoints.LoginController.class,
                io.micronaut.security.endpoints.LogoutController.class
        },
        tags = @Tag(name = "Security", description = "Security related stuffs")
)
@SecurityScheme(
        name = "api",
        scheme = "bearer",
        bearerFormat = "JWT",
        type = SecuritySchemeType.HTTP
)
public class OpenAPIConfiguration {}
