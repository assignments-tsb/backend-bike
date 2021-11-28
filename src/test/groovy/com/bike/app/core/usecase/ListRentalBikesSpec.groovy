package com.bike.app.core.usecase

import com.bike.app.core.Bike
import com.bike.app.core.adapters.datastore.BikeStore
import com.bike.app.core.usecase.impl.ListRentalBikesInteractor
import spock.lang.Specification

class ListRentalBikesSpec extends Specification {

    def "should list available bikes"() {

        given: "a fake bike store"
        BikeStore mockBikeStore = Mock(BikeStore)
        mockBikeStore.findAll() >> [new Bike()]

        and: "the use case to list"
        ListRentalBikes sut = new ListRentalBikesInteractor(mockBikeStore)

        when:
        List<Bike> availableBikes = sut.perform()

        then:
        availableBikes
    }
}
