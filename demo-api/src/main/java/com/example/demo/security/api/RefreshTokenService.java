package com.example.demo.security.api;

import com.example.demo.user.api.bo.User;

public interface RefreshTokenService {

    boolean validate(User user);

    void set(User user);

    void remove(User user);

}
