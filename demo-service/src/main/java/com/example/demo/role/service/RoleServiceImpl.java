package com.example.demo.role.service;

import com.example.demo.cache.api.RedisService;
import com.example.demo.framework.annotation.NotBlank;
import com.example.demo.framework.annotation.NotNull;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.service.impl.ServiceImpl;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.role.api.RoleMenuService;
import com.example.demo.role.api.RoleService;
import com.example.demo.role.api.bo.Role;
import com.example.demo.role.dao.dataobject.RoleDO;
import com.example.demo.role.dao.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleDO> implements RoleService {

    @Autowired
    private RedisService<String, Role> redisService;

    @Autowired
    private RoleMenuService            roleMenuService;

    @Override
    public int countRole(Role role) {
        if (role == null) {
            return 0;
        }

        return this.count(BeanUtil.copy(role, RoleDO.class));
    }

    @Override
    public List<Role> listRoles() {
        RoleDO roleDO = new RoleDO();
        roleDO.setPageNo(1);
        roleDO.setPageSize(99);

        return BeanUtil.copy(this.list(roleDO), Role.class);
    }

    @Override
    public List<Role> listRoles(Role role) {
        if (role == null) {
            return null;
        }

        return BeanUtil.copy(this.list(BeanUtil.copy(role, RoleDO.class)), Role.class);
    }

    @Override
    public Role getRole(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }

        return BeanUtil.copy(this.get(new RoleDO(new BigInteger(id))), Role.class);
    }

    @Override
    public Role getRole(@NotNull BigInteger id) {
        String key = RedisService.CACHE_KEY_ROLE + id;

        Role role = null;

        try {
            role = redisService.get(key);
        } catch (ServiceException e) {
            log.error(RedisService.CACHE_KEY_ROLE + "{}", key, e);
        }

        if (role != null) {
            return role;
        }

        role = BeanUtil.copy(this.get(new RoleDO(id)), Role.class);

        if (role == null) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "角色不存在");
        }

        try {
            redisService.set(key, role);
        } catch (ServiceException e) {
            log.error("{}", key, e);
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

        Role role = BeanUtil.copy(this.get(roleDO), Role.class);

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
            this.insert(roleDO);
        } catch (DuplicateKeyException e) {
            log.error("{}", roleDO, e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "编号已存在");
        } catch (Exception e) {
            log.error("{}", roleDO, e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        role.setId(roleDO.getId());

        roleMenuService.updateRoleMenus(role.getId(), role.getMenuIds(), creator);

        return role;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Role updateRole(@NotNull BigInteger id, @NotNull Role role, @NotBlank String modifier) {
        role.setId(id);

        RoleDO roleDO = BeanUtil.copy(role, RoleDO.class);
        roleDO.setModifier(modifier);

        try {
            if (this.update(roleDO) != 1) {
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("{}", roleDO, e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        roleMenuService.updateRoleMenus(role.getId(), role.getMenuIds(), modifier);

        remove(id);

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
            if (this.baseMapper.updateStatus(roleDO) != 1) {
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("{}", roleDO, e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        remove(id);

        return BeanUtil.copy(roleDO, Role.class);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Role deleteRole(@NotNull BigInteger id, @NotBlank String modifier) {
        RoleDO roleDO = new RoleDO();
        roleDO.setId(id);
        roleDO.setModifier(modifier);

        try {
            if (this.delete(roleDO) != 1) {
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            log.error("{}", roleDO, e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        roleMenuService.deleteRoleMenu(id, null, modifier);

        remove(id);

        return BeanUtil.copy(roleDO, Role.class);
    }

    private void remove(BigInteger id) {
        String key = RedisService.CACHE_KEY_ROLE + id;

        try {
            redisService.remove(key);
        } catch (Exception e) {
            log.error("{}", key, e);
        }
    }

}
