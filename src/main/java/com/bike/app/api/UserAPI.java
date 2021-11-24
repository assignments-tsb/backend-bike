package com.bike.app.api;

import com.bike.app.core.usecase.CreateUser;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.hateoas.JsonError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.annotation.security.RolesAllowed;

@RequiredArgsConstructor
@Controller("/user")
public class UserAPI {

    private final CreateUser createUser;

    @Schema(name = "UserCreateRequest", description = "A new system user")
    @Data @RequiredArgsConstructor @AllArgsConstructor
    public static class CreateUserRequest {

        @Schema(description = "Unique username for login", example = "admin")
        String username;

        @Schema(description = "User secret", example = "admin123")
        String password;

        @Schema(description = "The name that's displayed in the UI", example = "Administrator")
        String displayName;
    }

    @Value
    @Schema(name = "UserCreateResponse", description = "the system id")
    public static class CreatedUserResponse {

        @Schema(description = "System-generated, unique id", example = "1d5313be-713f-4650-bee3-2d0a18ffaa65")
        String id;
    }

    @Post(uri = "/", produces = MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    @Operation(operationId = "user-create", summary = "Create a new User", description = "Allows administrators to create a new User for the system")
    @ApiResponse(responseCode = "401", description = "Authentication is required")
    @ApiResponse(responseCode = "403", description = "User is not allowed to create a new user")
    @ApiResponse(responseCode = "409", description = "User already exists")
    @Tag(name = "User")
    public CreatedUserResponse create(@Body CreateUserRequest createUserRequest) throws CreateUser.UsernameAlreadyTaken {
        var createUser = new CreateUser.UserCreateCommand()
                .withUsername(createUserRequest.username)
                .withPlainPassword(createUserRequest.password)
                .withDisplayName(createUserRequest.displayName);

        var createdUser = this.createUser.perform(createUser);

        return new CreatedUserResponse(createdUser.getUserId());
    }

    @Error(exception = CreateUser.UsernameAlreadyTaken.class)
    public JsonError catchUsernameAlreadyTaken(CreateUser.UsernameAlreadyTaken exception) {

        JsonError error = new JsonError("Username '" + exception.getUsername() + "' is already taken");

        return error;
    }
}
