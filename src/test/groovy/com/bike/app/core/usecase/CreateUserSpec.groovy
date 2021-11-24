package com.bike.app.core.usecase

import com.bike.app.core.adapters.datastore.UserStore
import com.bike.app.core.adapters.security.PasswordEncryptor
import com.bike.app.core.usecase.impl.CreateUserImpl
import spock.lang.Specification

class CreateUserSpec extends Specification {

    private CreateUser createUserUseCase
    private UserStore mockUserStore
    private PasswordEncryptor mockPasswordEncryptor

    void setup() {
        mockUserStore = Stub(UserStore)
        mockPasswordEncryptor = Stub(PasswordEncryptor)
        createUserUseCase = new CreateUserImpl(mockUserStore, mockPasswordEncryptor)
    }

    def "should persist a user"() {

        given: "the user details"
        def user = new CreateUser.UserCreateCommand()
                .withDisplayName("Test")
                .withUsername("test")
                .withPlainPassword("test")

        when:
        def createdUser = createUserUseCase.perform(user)

        then:
        createdUser
    }


}
