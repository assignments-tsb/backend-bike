package com.bike.app.framework.openapi;

import io.micronaut.openapi.annotation.OpenAPIInclude;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "Bike Rental Service API",
                version = "1.0",
                description = "a simple CRUD web application for a bike rental shop",
                license = @License(name = "Apache 2.0", url = "https://codefactory.dev"),
                contact = @Contact(url = "https://bikerental.com", name = "LM Bibera", email = "secret.transaction@gmail.com")
        )
)
@OpenAPIInclude(
        classes = {
                io.micronaut.security.endpoints.LoginController.class,
                io.micronaut.security.endpoints.LogoutController.class
        }
)
public class OpenAPIConfiguration {
}
