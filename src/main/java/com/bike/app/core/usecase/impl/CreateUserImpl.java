package com.bike.app.core.usecase.impl;

import com.bike.app.core.User;
import com.bike.app.core.adapters.datastore.UserStore;
import com.bike.app.core.adapters.security.PasswordEncryptor;
import com.bike.app.core.usecase.CreateUser;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Singleton
public class CreateUserImpl implements CreateUser {

    private final UserStore userStore;
    private final PasswordEncryptor passwordEncryptor;

    @Override
    public CreatedUser perform(UserCreateCommand createCommand) {
        User user = new User()
                .withDisplayName(createCommand.getDisplayName())
                .withEncryptedPassword(passwordEncryptor.encrypt(createCommand.getPlainPassword()))
                .withUsername(createCommand.getUsername());

        User created = userStore.create(user);

        return new CreatedUser()
                .withUserId(created.getId())
                .withUsername(created.getUsername());
    }
}
