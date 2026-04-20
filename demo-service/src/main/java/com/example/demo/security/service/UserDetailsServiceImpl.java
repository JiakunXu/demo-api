package com.example.demo.security.service;

import com.example.demo.security.api.UserDetailsService;
import com.example.demo.security.api.bo.LoginUser;
import com.example.demo.user.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser user = userService.getUser(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        user.setAccountNonExpired(!user.getExpired());
        user.setAccountNonLocked(!user.getLocked());

        return user;
    }

    @Override
    public UserDetails loadUserById(BigInteger id) {
        return null;
    }

}
