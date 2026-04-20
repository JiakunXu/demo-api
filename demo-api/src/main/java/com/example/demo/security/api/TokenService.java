package com.example.demo.security.api;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {

    String TOKEN_PREFIX = "Bearer ";

    UserDetails getUser(String token);

    String setUser(UserDetails user);

    void refresh(String token);

    void remove(String token);

}
