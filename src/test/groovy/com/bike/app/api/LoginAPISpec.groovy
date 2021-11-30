package com.bike.app.api

import com.bike.app.test.SetupData
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
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

    def "should grant access to a secure endpoint after logging in"() {

        given: "the admin user credentials"
        def requestBody = """
            {
                "username": "${adminUsername}",
                "password": "${adminPassword}"
            }
        """

        when: "we login with the correct credentials"
        def loginResult = client.toBlocking().retrieve(HttpRequest.POST("/login", requestBody), Map<String, String>)

        and: "get the access token"
        String accessToken = loginResult['access_token']

        and: "we try to access an admin-only endpoint, using the admin credentials"
        def request = HttpRequest.POST("/user", """ 
            {
                "displayName": "${UUID.randomUUID()}",
                "username": "${UUID.randomUUID()}",
                "password": "${UUID.randomUUID()}"
            }
        """).bearerAuth(accessToken)
        HttpResponse<Object> createUserResult = client.toBlocking().exchange(request)

        then: "the request is allowed"
        createUserResult.status() == HttpStatus.OK
    }

    def "should restrict access to a secure endpoint after logging in if role is not allowed"() {

        given: "the staff user credentials"
        def requestBody = """
            {
                "username": "${staffUsername}",
                "password": "${staffPassword}"
            }
        """

        when: "we login with the correct credentials"
        def loginResult = client.toBlocking().retrieve(HttpRequest.POST("/login", requestBody), Map<String, String>)

        and: "get the access token"
        String accessToken = loginResult['access_token']

        and: "we try to access an admin-only endpoint, using the staff credentials"
        def request = HttpRequest.POST("/user", """ 
            {
                "displayName": "${UUID.randomUUID()}",
                "username": "${UUID.randomUUID()}",
                "password": "${UUID.randomUUID()}"
            }
        """).bearerAuth(accessToken)
        client.toBlocking().exchange(request)

        then: "the request is allowed"
        def e = thrown(HttpClientResponseException)
        e.status == HttpStatus.FORBIDDEN
    }
}
