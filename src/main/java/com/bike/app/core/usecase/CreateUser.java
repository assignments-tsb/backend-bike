package com.bike.app.core.usecase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

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

    CreatedUser perform(UserCreateCommand createCommand);
}
