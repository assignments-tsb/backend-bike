package com.bike.app.driver.security;

import com.bike.app.core.adapters.security.PasswordEncryptor;
import jakarta.inject.Singleton;

@Singleton
public class NoOpPasswordEncryptor implements PasswordEncryptor {

    @Override
    public String encrypt(String plainText) {
        return plainText;
    }
}
