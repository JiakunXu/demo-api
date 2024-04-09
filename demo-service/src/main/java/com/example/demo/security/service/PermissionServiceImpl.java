package com.example.demo.security.service;

import com.example.demo.security.api.PermissionService;
import com.example.demo.security.api.bo.Permission;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

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
