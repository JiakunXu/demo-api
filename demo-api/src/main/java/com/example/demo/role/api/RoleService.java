package com.example.demo.role.api;

import com.example.demo.role.api.bo.Role;

import java.math.BigInteger;
import java.util.List;

/**
 * @author JiakunXu
 */
public interface RoleService {

    String ROLE_ADMIN = "ROLE_ADMIN";

    void validate(BigInteger corpId, String id);

    void validate(BigInteger corpId, BigInteger id);

    int countRole(BigInteger corpId);

    int countRole(BigInteger corpId, Role role);

    List<Role> listRoles(BigInteger corpId);

    List<Role> listRoles(BigInteger corpId, Role role);

    Role getRole(BigInteger id);

    Role getRole(BigInteger corpId, String id);

    Role getRole(BigInteger corpId, BigInteger id);

    Role insertRole(BigInteger corpId, Role role, String creator);

    Role updateRole(BigInteger corpId, BigInteger id, Role role, String modifier);

    Role updateRole(BigInteger corpId, BigInteger id, String status, String modifier);

    Role deleteRole(BigInteger corpId, BigInteger id, String modifier);

}
