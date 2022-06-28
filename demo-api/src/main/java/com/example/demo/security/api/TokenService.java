package com.example.demo.security.api;

import com.example.demo.security.api.bo.LoginUser;

public interface TokenService {

    String TOKEN_PREFIX = "Bearer ";

    LoginUser getUser(String token);

    String setUser(LoginUser user);

    void refresh(String token);

    void remove(String token);

}
