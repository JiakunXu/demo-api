package com.example.demo.user.api;

import com.example.demo.security.api.bo.LoginUser;

import java.math.BigInteger;

public interface UserLoginService {

    LoginUser getUser(BigInteger id);

    LoginUser getUser(String username);

}
