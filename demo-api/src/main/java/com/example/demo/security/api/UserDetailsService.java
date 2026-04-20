package com.example.demo.security.api;

import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigInteger;

public interface UserDetailsService extends
                                    org.springframework.security.core.userdetails.UserDetailsService {

    UserDetails loadUserById(BigInteger id);

}
