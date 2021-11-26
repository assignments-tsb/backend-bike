package com.bike.app.driver.datastore.orm;

import com.bike.app.core.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "users")
public class ORMUser {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "user_id", updatable = false, nullable = false)
    String id;

    @Column(name = "display_name")
    String displayName;

    @Column(name = "username")
    String username;

    @Column(name = "encrypted_password")
    String encryptedPassword;

    public User asDomain() {
        return new User()
                .withId(id)
                .withUsername(username)
                .withDisplayName(displayName);
    }
}
