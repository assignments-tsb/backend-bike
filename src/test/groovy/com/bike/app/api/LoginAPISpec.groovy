package com.bike.app.api

import com.bike.app.test.SetupData
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@MicronautTest(environments = ["test"])
class LoginAPISpec extends Specification implements SetupData {

    @PersistenceContext
    EntityManager em

    @Inject
    @Client('/')
    HttpClient client

    def "should return the id after creating the user"() {

        given: "the admin user credentials"
        def requestBody = """
            {
                "username": "${adminUsername}",
                "password": "${adminPassword}"
            }
        """

        when: "we login with the correct credentials"
        def result = client.toBlocking().retrieve(HttpRequest.POST("/login", requestBody), Map<String, String>)

        then: "we get an access token in return"
        result["access_token"]

        and: "has the correct roles"
        result["roles"]
    }
}
