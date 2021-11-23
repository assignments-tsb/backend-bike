package com.bike.app.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@With @Getter
public class User {

    String id;
    String displayName;
    String username;
    String encryptedPassword;

    List<Role> roles;
}
