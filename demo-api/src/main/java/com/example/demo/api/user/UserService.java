package com.example.demo.api.user;

import com.example.demo.api.user.bo.User;

import java.math.BigInteger;

/**
 * @author JiakunXu
 */
public interface UserService {

    /**
     * 
     * @param id
     * @return
     */
    User getUser(BigInteger id);

}
