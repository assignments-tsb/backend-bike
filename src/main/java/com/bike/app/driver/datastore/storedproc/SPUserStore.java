package com.bike.app.driver.datastore.storedproc;

import com.bike.app.core.User;
import com.bike.app.core.adapters.datastore.UserStore;
import com.bike.app.driver.datastore.FeatureStoredProcedurePersistence;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@RequiredArgsConstructor
@Singleton
@FeatureStoredProcedurePersistence
public class SPUserStore implements UserStore {

    private final DataSource dataSource;

    @Override
    public User create(User user) throws NonUniqueUsername {
        return null;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try (Connection connection = dataSource.getConnection();
             CallableStatement statement = connection.prepareCall("{ ? = CALL user_find_by_username(?)}")) {

            statement.registerOutParameter(1, Types.OTHER);
            statement.setString(2, username);
            statement.execute();

            ResultSet results = (ResultSet) statement.getObject(1);
            while (results.next()) {
                System.out.println(results.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }

        return Optional.empty();
    }
}
