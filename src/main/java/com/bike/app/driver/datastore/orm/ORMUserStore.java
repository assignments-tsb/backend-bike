package com.bike.app.driver.datastore.orm;

import com.bike.app.core.User;
import com.bike.app.core.adapters.datastore.UserStore;
import com.bike.app.driver.datastore.FeatureORMPersistence;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.Optional;

@RequiredArgsConstructor
@Singleton
@FeatureORMPersistence
public class ORMUserStore implements UserStore {

    private final EntityManager em;

    @Override
    public User create(User user) throws NonUniqueUsername {
        return null;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            return Optional.ofNullable(em
                    .createQuery("select u from ORMUser u where u.username = :username", ORMUser.class)
                    .setParameter("username", username)
                    .getSingleResult().asDomain());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
