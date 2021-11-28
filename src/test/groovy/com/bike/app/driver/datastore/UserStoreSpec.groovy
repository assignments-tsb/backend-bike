package com.bike.app.driver.datastore

import com.bike.app.core.User
import com.bike.app.core.adapters.datastore.UserStore
import com.bike.app.driver.datastore.orm.ORMUser
import jakarta.inject.Inject
import spock.lang.Specification

import javax.persistence.EntityManager

abstract class UserStoreSpec extends Specification {

    @Inject
    EntityManager em

    @Inject
    UserStore userStore

    def "should persist a new instance of user"() {

        given: "the details for the new user"
        String username = "test123"
        String displayName = "Test User"
        String encryptedPassword = "u128383838323jj"

        and: "a new user object without an id"
        User user = new User()
                .withUsername(username)
                .withDisplayName(displayName)
                .withEncryptedPassword(encryptedPassword)

        when: "the user is persisted"
        User createdUser = userStore.create(user)

        then: "a new instance of the user is created in the persistent context"
        createdUser != user

        and: "an id is generated"
        createdUser.getId()

        and: "all the features are still present"
        createdUser.getUsername() == username
        createdUser.getDisplayName() == displayName
        createdUser.getEncryptedPassword() == encryptedPassword
    }

    def "should retrieve an instance of a user when it matches the username"() {

        given: "a username of a user that we want to search"
        String username = "testuser"
        String displayName = "Test User"
        String password = "test123"

        and: "we persist that user"
        createUser(displayName, username, password)

        when: "we retrieve the user filtered by username"
        Optional<User> retrievedUser = userStore.findByUsername(username)

        then: "the user exists"
        retrievedUser.isPresent()
        def user = retrievedUser.get()

        and: "the details are preserved"
        user.getUsername() == username
        user.getDisplayName() == displayName
        user.getEncryptedPassword() == password
    }

    private void createUser(String displayName, String username, String password) {

        ORMUser dummyUser = new ORMUser()
        dummyUser.setDisplayName(displayName)
        dummyUser.setUsername(username)
        dummyUser.setEncryptedPassword(password)
        em.persist(dummyUser)
        em.flush()
    }
}
