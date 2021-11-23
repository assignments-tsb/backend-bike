package com.bike.app.framework.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "Bike Rental Service API",
                version = "1.0",
                description = "a simple CRUD web applica<on for a bike rental shop",
                license = @License(name = "Apache 2.0", url = "https://codefactory.dev"),
                contact = @Contact(url = "https://bikerental.com", name = "LM Bibera", email = "secret.transaction@gmail.com")
        )
)
public class OpenAPIConfiguration {
}
