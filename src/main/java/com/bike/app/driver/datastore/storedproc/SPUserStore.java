package com.bike.app.driver.datastore.storedproc;

import com.bike.app.core.User;
import com.bike.app.core.adapters.datastore.UserStore;
import com.bike.app.driver.datastore.FeatureStoredProcedurePersistence;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Singleton
@FeatureStoredProcedurePersistence
public class SPUserStore implements UserStore {

    private static final String SQL_FIND_BY_USERNAME = "SELECT * FROM user_find_by_username(?)";
    private static final String SQL_CREATE = "CALL user_create(?,?,?)";

    private final DataSource dataSource;

    @Override
    public User create(User user) throws NonUniqueUsername {
        try (Connection connection = dataSource.getConnection();
             CallableStatement statement = connection.prepareCall(SQL_CREATE)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getDisplayName());
            statement.setString(3, "");


            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String id = result.getString(1);

                return new User()
                        .withId(id)
                        .withDisplayName(user.getDisplayName())
                        .withEncryptedPassword(user.getEncryptedPassword())
                        .withUsername(user.getUsername());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareCall(SQL_FIND_BY_USERNAME)) {
            statement.setString(1, username);

            ResultSet results = statement.executeQuery();
            if (results.next()) {
                User user = new User()
                        .withId(results.getString("user_id"))
                        .withDisplayName(results.getString("display_name"))
                        .withEncryptedPassword(results.getString("encrypted_password"))
                        .withUsername(results.getString("username"));

                return Optional.of(user);
            }
            results.close();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }

        return Optional.empty();
    }
}
