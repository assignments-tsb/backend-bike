package com.bike.app.framework.security;

import com.bike.app.core.usecase.Login;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Singleton
public class AppAuthenticationProvider implements AuthenticationProvider {

    private final Login loginUseCase;

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Flux.create(emitter -> {
            var username = (String) authenticationRequest.getIdentity();
            var password = (String) authenticationRequest.getSecret();

            try {
                var user = loginUseCase.perform(username, password);

                emitter.next(AuthenticationResponse.success(user.getUsername(), user.getRoles()));
                emitter.complete();
            } catch (Login.UserNotFound | Login.IncorrectPassword userNotFound) {
                emitter.error(userNotFound);
            }
        });
    }
}
