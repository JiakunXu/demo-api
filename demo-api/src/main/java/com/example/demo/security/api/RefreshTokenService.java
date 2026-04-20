package com.example.demo.security.api;

import com.example.demo.user.api.bo.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface RefreshTokenService {

    boolean validate(UserDetails user);

    void set(UserDetails user);

    void remove(User user);

}
