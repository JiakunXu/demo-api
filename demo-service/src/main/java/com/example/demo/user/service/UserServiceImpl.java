package com.example.demo.user.service;

import com.example.demo.user.api.UserService;
import com.example.demo.user.api.bo.User;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * @author JiakunXu
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(BigInteger id) {
        return null;
    }

}
