package com.bike.app.driver.datastore.orm;

import com.bike.app.core.Role;
import com.bike.app.core.User;
import io.micronaut.core.util.CollectionUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
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

    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    List<ORMRole> roles;

    public static ORMUser from(User user) {
        ORMUser ormUser = new ORMUser();
        ormUser.setId(user.getId());
        ormUser.setDisplayName(user.getDisplayName());
        ormUser.setUsername(user.getUsername());
        ormUser.setEncryptedPassword(user.getEncryptedPassword());

        return ormUser;
    }

    public User toEntity() {
        List<Role> roleEntities = CollectionUtils.isEmpty(roles)
                ? Collections.emptyList()
                : roles.stream().map(ORMRole::toEntity).collect(Collectors.toList());

        return new User()
                .withId(id)
                .withUsername(username)
                .withDisplayName(displayName)
                .withRoles(roleEntities)
                .withEncryptedPassword(encryptedPassword);
    }
}
