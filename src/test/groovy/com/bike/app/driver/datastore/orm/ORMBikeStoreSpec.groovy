package com.bike.app.driver.datastore.orm

import com.bike.app.driver.datastore.BikeStoreSpec
import io.micronaut.test.extensions.spock.annotation.MicronautTest

@MicronautTest(environments = ["orm"])
class ORMBikeStoreSpec extends BikeStoreSpec {

    def "should wire the ORMBikeStore"() {
        expect:
        bikeStore instanceof ORMBikeStore
    }
}
