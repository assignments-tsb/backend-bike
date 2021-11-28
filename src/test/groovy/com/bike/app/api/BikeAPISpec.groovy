package com.bike.app.api

import com.bike.app.test.BikePreparer
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

import javax.persistence.EntityManager

@MicronautTest(environments = ["api"])
class BikeAPISpec extends Specification implements BikePreparer {

    @Inject
    EntityManager em

    def "should list bikes"() {

    }

    public EntityManager getEM() {
        return em;
    }
}
