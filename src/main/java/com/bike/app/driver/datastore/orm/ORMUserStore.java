package com.bike.app.driver.datastore.orm;

import com.bike.app.core.User;
import com.bike.app.core.adapters.datastore.UserStore;
import com.bike.app.driver.datastore.FeatureORMPersistence;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
@FeatureORMPersistence
public class ORMUserStore implements UserStore {

    @Override
    public User create(User user) throws NonUniqueUsername {
        return null;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }
}
