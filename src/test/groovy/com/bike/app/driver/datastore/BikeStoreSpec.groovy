package com.bike.app.driver.datastore

import com.bike.app.core.Bike
import com.bike.app.core.adapters.datastore.BikeStore
import com.bike.app.test.BikePreparer
import jakarta.inject.Inject
import spock.lang.Specification

import javax.persistence.EntityManager

abstract class BikeStoreSpec extends Specification implements BikePreparer {

    @Inject
    EntityManager em

    @Inject
    BikeStore bikeStore

    def "should list all bikes"() {

        given: "insert a couple of bikes"
        prepareTestBikes()

        when: "data is fetched"
        List<Bike> allBikes = bikeStore.findAll()

        then: "get a list of all bikes"
        allBikes
    }
}
