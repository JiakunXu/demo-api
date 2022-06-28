package com.example.demo.security.api;

import java.math.BigInteger;
import java.util.List;

import com.example.demo.security.api.bo.Permission;

public interface PermissionService {

    void validate(BigInteger userId, String authority);

    List<Permission> listPermissions(BigInteger userId);

}
