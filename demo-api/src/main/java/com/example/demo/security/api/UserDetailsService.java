package com.example.demo.security.api;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService extends
                                    org.springframework.security.core.userdetails.UserDetailsService {

    UserDetails loadUserByCode(String code);

}
