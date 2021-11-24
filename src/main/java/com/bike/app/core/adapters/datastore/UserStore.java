package com.bike.app.core.adapters.datastore;

import com.bike.app.core.User;

import java.util.Optional;

public interface UserStore {

    class NonUniqueUsername extends Exception {}

    User create(User user) throws NonUniqueUsername;

    Optional<User> findByUsername(String username);
}
