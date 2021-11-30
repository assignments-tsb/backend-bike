package com.bike.app.driver.datastore.orm


import com.bike.app.driver.datastore.UserStoreSpec
import io.micronaut.test.extensions.spock.annotation.MicronautTest

@MicronautTest(environments = ["orm"])
class ORMUserStoreSpec extends UserStoreSpec {

    def "should wire an instance of ORMUserStore"() {
        expect:
        userStore instanceof ORMUserStore
    }

}
