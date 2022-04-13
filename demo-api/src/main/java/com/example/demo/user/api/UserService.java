package com.example.demo.user.api;

import com.example.demo.user.api.bo.User;

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
