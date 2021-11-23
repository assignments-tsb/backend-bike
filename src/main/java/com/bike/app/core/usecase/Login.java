package com.bike.app.core.usecase;

import lombok.Value;

import java.util.List;

public interface Login {

    @Value
    class UserInformation {
        String id, displayName, username;
        List<String> roles;
    }

    class UserNotFound extends Exception {
        public UserNotFound(String username) {
            super("User with the following username does not exist: " + username);
        }
    }

    class IncorrectPassword extends Exception {
        public IncorrectPassword() {
            super("Password is incorrect");
        }
    }

    UserInformation perform(String username, String password) throws UserNotFound, IncorrectPassword;
}
