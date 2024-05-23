package com.example.demo.user.service;

import com.example.demo.security.api.bo.LoginUser;
import com.example.demo.user.api.UserService;
import com.example.demo.user.api.bo.User;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * @author JiakunXu
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public void validate(BigInteger corpId, String id) {

    }

    @Override
    public void validate(BigInteger corpId, BigInteger id) {

    }

    @Override
    public User getUser(BigInteger id) {
        return null;
    }

    @Override
    public LoginUser getUser(String username) {
        return null;
    }

    @Override
    public User refreshToken(BigInteger corpId, BigInteger id, String modifier) {
        return null;
    }

    @Override
    public List<User> refreshToken(BigInteger corpId, BigInteger[] ids, String modifier) {
        return List.of();
    }

}
