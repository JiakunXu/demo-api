package com.example.demo.security.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.security.api.PermissionService;
import com.example.demo.security.api.bo.Permission;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Override
    public void validate(BigInteger userId, String authority) {
    }

    @Override
    public List<Permission> listPermissions(BigInteger userId) {
        return null;
    }

}
