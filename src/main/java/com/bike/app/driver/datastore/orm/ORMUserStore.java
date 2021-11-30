package com.bike.app.driver.datastore.orm;

import com.bike.app.core.User;
import com.bike.app.core.adapters.datastore.UserStore;
import com.bike.app.driver.datastore.FeatureORMPersistence;
import io.micronaut.transaction.annotation.ReadOnly;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Singleton
@FeatureORMPersistence
public class ORMUserStore implements UserStore {

    @PersistenceContext
    private final EntityManager em;

    @Transactional
    @Override
    public User create(User user) throws NonUniqueUsername {
        ORMUser ormUser = ORMUser.from(user);
        em.persist(ormUser);

        return ormUser.toEntity();
    }

    @ReadOnly
    @Override
    public Optional<User> findByUsername(String username) {
        try {
            return Optional.ofNullable(em
                    .createQuery("select u from ORMUser u where u.username = :username", ORMUser.class)
                    .setParameter("username", username)
                    .getSingleResult().toEntity());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }
}
