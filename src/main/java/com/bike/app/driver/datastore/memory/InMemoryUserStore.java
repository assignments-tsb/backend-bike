package com.bike.app.driver.datastore.memory;

import com.bike.app.core.Role;
import com.bike.app.core.User;
import com.bike.app.core.adapters.datastore.UserStore;
import com.bike.app.driver.datastore.FeatureInMemoryPersistence;
import jakarta.inject.Singleton;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Singleton
@FeatureInMemoryPersistence
public class InMemoryUserStore implements UserStore {

    private final HashMap<String, User> users = new HashMap<>();

    public InMemoryUserStore() {
        var adminRole = new Role("ADMIN", "Admin");
        var adminUser = new User().withId(UUID.randomUUID().toString())
                .withRoles(Arrays.asList(adminRole))
                .withUsername("admin")
                .withEncryptedPassword("admin123");

        users.put(adminUser.getId(), adminUser);

        var staffRole = new Role("STAFF", "Staff");
        var staffUser = new User()
                .withId(UUID.randomUUID().toString())
                .withRoles(Arrays.asList(staffRole))
                .withUsername("staff")
                .withEncryptedPassword("staff123");

        users.put(staffUser.getId(), staffUser);
    }

    @Override
    public User create(User user) throws NonUniqueUsername {
        throwDuplicateUsernameIfAlreadyExists(user.getUsername());

        var generatedId = UUID.randomUUID().toString();
        var createdUser = user.withId(generatedId);
        users.put(generatedId, createdUser);

        return createdUser;
    }

    private void throwDuplicateUsernameIfAlreadyExists(String username) throws NonUniqueUsername {
        var alreadyExists = users.values().stream()
                .anyMatch(u -> u.getUsername().equals(username));

        if (alreadyExists) throw new UserStore.NonUniqueUsername();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        for (User user : users.values()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }
}
