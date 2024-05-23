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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public int countRole(Role role) {
        if (role == null) {
            return 0;
        }

        return count(BeanUtil.copy(role, RoleDO.class));
    }

    @Override
    public List<Role> listRoles(Role role) {
        if (role == null) {
            return null;
        }

        return BeanUtil.copy(list(BeanUtil.copy(role, RoleDO.class)), Role.class);
    }

    @Override
    public List<Role> listRoles() {
        RoleDO roleDO = new RoleDO();
        roleDO.setPageNo(1);
        roleDO.setPageSize(99);

        return BeanUtil.copy(list(roleDO), Role.class);
    }

    @Override
    public Role getRole(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }

        return BeanUtil.copy(get(new RoleDO(new BigInteger(id))), Role.class);
    }

    @Override
    public Role getRole(BigInteger id) {
        if (id == null) {
            return null;
        }

        String key = id.toString();

        Role role = null;

        try {
            role = redisService.get(RedisService.CACHE_KEY_ROLE_ID + key);
        } catch (ServiceException e) {
            logger.error(RedisService.CACHE_KEY_ROLE_ID + key, e);
        }

        if (role != null) {
            return role;
        }

        role = BeanUtil.copy(get(new RoleDO(id)), Role.class);

        if (role == null) {
            return null;
        }

        try {
            redisService.set(RedisService.CACHE_KEY_ROLE_ID + key, role);
        } catch (ServiceException e) {
            logger.error(RedisService.CACHE_KEY_ROLE_ID + key, e);
        }

        return role;
    }

    @Override
    public BigInteger getRoleId(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }

        RoleDO roleDO = new RoleDO();
        roleDO.setCode(code);

        Role role = BeanUtil.copy(get(roleDO), Role.class);

        if (role == null) {
            return null;
        }

        return role.getId();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Role insertRole(@NotNull Role role, @NotBlank String creator) {
        RoleDO roleDO = BeanUtil.copy(role, RoleDO.class);
        roleDO.setCreator(creator);

        try {
            roleMapper.insert(roleDO);
        } catch (DuplicateKeyException e) {
            logger.error(roleDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "编号已存在");
        } catch (Exception e) {
            logger.error(roleDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        role.setId(roleDO.getId());

        roleMenuService.updateRoleMenu(role.getId(), role.getMenuIds(), creator);

        return role;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Role updateRole(@NotNull BigInteger id, @NotNull Role role, @NotBlank String modifier) {
        role.setId(id);

        RoleDO roleDO = BeanUtil.copy(role, RoleDO.class);
        roleDO.setModifier(modifier);

        try {
            if (roleMapper.update(roleDO) != 1) {
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            logger.error(roleDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        roleMenuService.updateRoleMenu(role.getId(), role.getMenuIds(), modifier);

        remove(id.toString());

        return role;
    }

    @Override
    public Role updateRole(@NotNull BigInteger id, @NotBlank String status,
                           @NotBlank String modifier) {
        RoleDO roleDO = new RoleDO();
        roleDO.setId(id);
        roleDO.setStatus(status);
        roleDO.setModifier(modifier);

        try {
            if (roleMapper.updateStatus(roleDO) != 1) {
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            logger.error(roleDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        remove(id.toString());

        return BeanUtil.copy(roleDO, Role.class);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Role deleteRole(@NotNull BigInteger id, @NotBlank String modifier) {
        if (userRoleService.countUserRole(id) > 0) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "已关联用户，请先调整用户角色");
        }

        RoleDO roleDO = new RoleDO();
        roleDO.setId(id);
        roleDO.setModifier(modifier);

        try {
            if (roleMapper.delete(roleDO) != 1) {
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            logger.error(roleDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        roleMenuService.deleteRoleMenu(id, null, modifier);

        remove(id.toString());

        return BeanUtil.copy(roleDO, Role.class);
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
