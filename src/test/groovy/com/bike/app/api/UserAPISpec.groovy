package com.bike.app.api


import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest
class UserAPISpec extends Specification {

    @Inject
    @Client('/')
    HttpClient client

    def "should return the id after creating the user"() {
        given:
        def requestBody = '''
            {
                "username": "test123",
                "password": "pass123",
                "displayName": "Test"
            }
        '''

        when:
        def result = client.toBlocking().retrieve(HttpRequest.POST("/user", requestBody), Map<String, String>)
        String id = result["id"]

        then:
        !id.isEmpty()
    }

}
