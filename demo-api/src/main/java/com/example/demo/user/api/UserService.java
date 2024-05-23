package com.example.demo.user.api;

import com.example.demo.security.api.bo.LoginUser;
import com.example.demo.user.api.bo.User;

import java.math.BigInteger;
import java.util.List;

/**
 * @author JiakunXu
 */
public interface UserService {

    void validate(BigInteger corpId, String id);

    void validate(BigInteger corpId, BigInteger id);

    User getUser(BigInteger id);

    User getUser(BigInteger corpId, String id);

    User getUser(BigInteger corpId, BigInteger id);

    LoginUser getUser(String username);

    User refreshToken(BigInteger corpId, BigInteger id, String modifier);

    List<User> refreshToken(BigInteger corpId, BigInteger[] ids, String modifier);

}
