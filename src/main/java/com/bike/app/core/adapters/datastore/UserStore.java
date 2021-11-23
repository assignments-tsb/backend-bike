package com.bike.app.core.adapters.datastore;

import com.bike.app.core.User;

import java.util.Optional;

public interface UserStore {

    User create(User user);

    Optional<User> findByUsername(String username);
}
