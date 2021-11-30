package com.bike.app.driver.datastore.storedproc


import com.bike.app.driver.datastore.UserStoreSpec
import io.micronaut.test.extensions.spock.annotation.MicronautTest

@MicronautTest(environments = ["stored-proc"])
class SPUserStoreSpec extends UserStoreSpec {

    def "should wire the stored procedure variant of UserStore"() {
        expect:
        userStore instanceof SPUserStore
    }

}
