package com.bike.app.core.usecase;

import lombok.*;

public interface CreateUser {

    @Getter
    @With
    @AllArgsConstructor
    @NoArgsConstructor
    class UserCreateCommand {
        String username, plainPassword, displayName;
    }

    @Getter
    @With
    @AllArgsConstructor
    @NoArgsConstructor
    class CreatedUser {
        String userId, username;
    }

    @Value
    class UsernameAlreadyTaken extends Exception {
        String username;
    }

    CreatedUser perform(UserCreateCommand createCommand) throws UsernameAlreadyTaken;
}
