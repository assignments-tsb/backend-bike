package com.bike.app.core.usecase.impl;

import com.bike.app.core.Role;
import com.bike.app.core.User;
import com.bike.app.core.adapters.datastore.UserStore;
import com.bike.app.core.adapters.security.PasswordEncryptor;
import com.bike.app.core.usecase.Login;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Singleton
public class LoginInteractor implements Login {

    private final UserStore userStore;
    private final PasswordEncryptor passwordEncryptor;

    @Override
    public UserInformation perform(String username, String password) throws UserNotFound, IncorrectPassword {
        var user = findUserOrThrowUserNotFound(username);
        checkPassword(user, password);

        var roleIds = user.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toList());

        return new UserInformation(user.getId(), user.getDisplayName(), user.getUsername(), roleIds);
    }

    private User findUserOrThrowUserNotFound(String username) throws UserNotFound {
        return userStore.findByUsername(username)
                .orElseThrow(() -> new UserNotFound(username));
    }

    private void checkPassword(User user, String password) throws IncorrectPassword {
        if (!user.getEncryptedPassword().equals(passwordEncryptor.encrypt(password))) {
            throw new IncorrectPassword();
        }
    }
}
