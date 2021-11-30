package com.bike.app.api

import com.bike.app.test.BikePreparer
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@MicronautTest(environments = ["api"])
class BikeAPISpec extends Specification implements BikePreparer {

    @PersistenceContext
    EntityManager em

    @Inject
    @Client('/')
    HttpClient client

    def "should list bikes"() {

        given: "a couple of bikes prepared"
        prepareTestBikes()

        when:
        def bikes = client.toBlocking().exchange("/bikes")

        then:
        bikes
    }
}
