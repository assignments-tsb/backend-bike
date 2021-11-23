package com.bike.app.api;

import com.bike.app.core.usecase.CreateUser;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import javax.annotation.security.RolesAllowed;

@RequiredArgsConstructor
@Controller("/user")
public class UserAPI {

    private final CreateUser createUser;

    @Schema(name = "CreateUser")
    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class CreateUserRequest {
        String username;
        String password;
        String displayName;
    }

    @Value
    public static class CreatedUserResponse {
        String userId;
    }

    @Post(uri = "/")
    @Operation()
    @RolesAllowed({"ADMIN"})
    public CreatedUserResponse create(@Body CreateUserRequest createUserRequest) {
        var createUser = new CreateUser.UserCreateCommand()
                .withUsername(createUserRequest.username)
                .withPlainPassword(createUserRequest.password)
                .withDisplayName(createUserRequest.displayName);

        var createdUser = this.createUser.perform(createUser);

        return new CreatedUserResponse(createdUser.getUserId());
    }
}
