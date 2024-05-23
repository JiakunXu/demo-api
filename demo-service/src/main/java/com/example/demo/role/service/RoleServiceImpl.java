package com.example.demo.role.service;

import com.example.demo.cache.api.RedisService;
import com.example.demo.framework.annotation.NotBlank;
import com.example.demo.framework.annotation.NotNull;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.role.api.RoleMenuService;
import com.example.demo.role.api.RoleService;
import com.example.demo.role.api.bo.Role;
import com.example.demo.role.dao.dataobject.RoleDO;
import com.example.demo.role.dao.mapper.RoleMapper;
import com.example.demo.user.api.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger        logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RedisService<String, Role> redisService;

    @Autowired
    private RoleMenuService            roleMenuService;

    @Autowired
    private UserRoleService            userRoleService;

    @Autowired
    private RoleMapper                 roleMapper;

    @Override
    public void validate(BigInteger corpId, @NotBlank String id) {
        validate(corpId, new BigInteger(id));
    }

    @Override
    public void validate(@NotNull BigInteger corpId, @NotNull BigInteger id) {
        RoleDO roleDO = new RoleDO();
        roleDO.setId(id);
        roleDO.setCorpId(corpId);

        if (count(roleDO) > 0) {
            return;
        }

        throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限（角色）");
    }

    @Override
    public int countRole(BigInteger corpId) {
        return 0;
    }

    @Override
    public int countRole(BigInteger corpId, Role role) {
        if (corpId == null || role == null) {
            return 0;
        }

        role.setCorpId(corpId);

        return count(BeanUtil.copy(role, RoleDO.class));
    }

    @Override
    public List<Role> listRoles(BigInteger corpId) {
        return List.of();
    }

    @Override
    public List<Role> listRoles(BigInteger corpId, Role role) {
        return List.of();
    }

    @Override
    public Role getRole(BigInteger id) {
        return null;
    }

    @Override
    public Role getRole(BigInteger corpId, String id) {
        return null;
    }

    @Override
    public Role insertRole(BigInteger corpId, Role role, String creator) {
        return null;
    }

    @Override
    public Role updateRole(BigInteger corpId, BigInteger id, Role role, String modifier) {
        return null;
    }

    @Override
    public Role updateRole(BigInteger corpId, BigInteger id, String status, String modifier) {
        return null;
    }

    @Override
    public Role deleteRole(BigInteger corpId, BigInteger id, String modifier) {
        return null;
    }

    private void remove(String key) {
        try {
            redisService.remove(RedisService.CACHE_KEY_ROLE_ID + key);
        } catch (Exception e) {
            logger.error(RedisService.CACHE_KEY_ROLE_ID + key, e);
        }
    }

    private int count(RoleDO roleDO) {
        try {
            return roleMapper.count(roleDO);
        } catch (Exception e) {
            logger.error(roleDO.toString(), e);
        }

        return 0;
    }

    private List<RoleDO> list(RoleDO roleDO) {
        try {
            return roleMapper.list(roleDO);
        } catch (Exception e) {
            logger.error(roleDO.toString(), e);
        }

        return null;
    }

    private RoleDO get(RoleDO roleDO) {
        try {
            return roleMapper.get(roleDO);
        } catch (Exception e) {
            logger.error(roleDO.toString(), e);
        }

        return null;
    }

}
