package com.bike.app.driver.datastore.storedproc

import com.bike.app.core.User
import com.bike.app.core.adapters.datastore.UserStore
import com.bike.app.driver.datastore.orm.ORMUser
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

import javax.persistence.EntityManager

@MicronautTest(environments = ["test", "stored-proc"])
class SPUserStoreSpec extends Specification {

    @Inject
    EntityManager em

    @Inject
    UserStore userStore

    def "should wire the stored procedure variant of user store"() {
        expect:
        userStore instanceof SPUserStore
    }

    def "should retrieve an instance of a user when it matches the username"() {
        given: "a username of a user that we want to search"
        String username = "testuser"

        and: "we persist that user"
        ORMUser dummyUser = new ORMUser()
        dummyUser.setUsername(username)
        dummyUser.setDisplayName("Test User")
        em.persist(dummyUser)

        when: "we retrieve the user filtered by username"
        Optional<User> retrievedUser = userStore.findByUsername(username)

        then: "the user exists"
        retrievedUser.isPresent()
        def user = retrievedUser.get()

        and: "the details are preserved"
        user.getUsername() == username
        user.getDisplayName() == "Test User"
    }
}
