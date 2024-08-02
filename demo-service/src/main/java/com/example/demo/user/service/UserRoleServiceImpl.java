package com.example.demo.user.service;

import com.example.demo.framework.annotation.NotBlank;
import com.example.demo.framework.annotation.NotNull;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.role.api.RoleService;
import com.example.demo.role.api.bo.Role;
import com.example.demo.user.api.UserRoleService;
import com.example.demo.user.api.UserService;
import com.example.demo.user.api.bo.User;
import com.example.demo.user.api.bo.UserRole;
import com.example.demo.user.dao.dataobject.UserDO;
import com.example.demo.user.dao.dataobject.UserRoleDO;
import com.example.demo.user.dao.mapper.UserRoleMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private static final Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);

    @Autowired
    private RoleService         roleService;

    @Autowired
    private UserService         userService;

    @Autowired
    private UserRoleMapper      userRoleMapper;

    @Override
    public int countUserRole(BigInteger roleId) {
        if (roleId == null) {
            return 0;
        }

        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setRoleId(roleId);

        return count(userRoleDO);
    }

    @Override
    public int countUserRole(BigInteger userId, String roleCode) {
        if (userId == null || StringUtils.isBlank(roleCode)) {
            return 0;
        }

        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setUserId(userId);
        userRoleDO.setCode(roleCode);

        return count(userRoleDO);
    }

    @Override
    public int countUserRole(BigInteger userId, String... roleCode) {
        if (userId == null || roleCode == null || roleCode.length == 0) {
            return 0;
        }

        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setUserId(userId);
        userRoleDO.setCodes(roleCode);

        return count(userRoleDO);
    }

    @Override
    public List<UserRole> listUserRoles(String userId) {
        if (StringUtils.isBlank(userId)) {
            return null;
        }

        return listUserRoles(new BigInteger(userId));
    }

    @Override
    public List<UserRole> listUserRoles(BigInteger userId) {
        if (userId == null) {
            return null;
        }

        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setUserId(userId);

        return BeanUtil.copy(list(userRoleDO), UserRole.class);
    }

    @Override
    public List<Role> listRoles(BigInteger userId, String status) {
        if (userId == null) {
            return null;
        }

        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setUserId(userId);
        userRoleDO.setStatus(status);

        List<UserRole> userRoleList = BeanUtil.copy(list(userRoleDO), UserRole.class);

        if (userRoleList == null || userRoleList.isEmpty()) {
            return null;
        }

        List<Role> list = new ArrayList<>();

        for (UserRole userRole : userRoleList) {
            list.add(roleService.getRole(userRole.getRoleId()));
        }

        return list;
    }

    @Override
    public int countUser(BigInteger corpId, String roleId, String exists, User user) {
        if (corpId == null || StringUtils.isBlank(roleId) || user == null) {
            return 0;
        }

        // TODO roleService.validate(corpId, roleId);

        user.setCorpId(corpId);

        UserDO userDO = BeanUtil.copy(user, UserDO.class);
        userDO.setRoleId(new BigInteger(roleId));
        userDO.setExists("true".equals(exists));

        return count(userDO);
    }

    @Override
    public List<User> listUsers(BigInteger corpId, String roleId, String exists, User user) {
        if (corpId == null || StringUtils.isBlank(roleId) || user == null) {
            return null;
        }

        user.setCorpId(corpId);

        UserDO userDO = BeanUtil.copy(user, UserDO.class);
        userDO.setRoleId(new BigInteger(roleId));
        userDO.setExists("true".equals(exists));

        return BeanUtil.copy(list(userDO), User.class);
    }

    @Override
    public List<User> listUsers(BigInteger corpId, String... roleCode) {
        if (corpId == null || roleCode == null || roleCode.length == 0) {
            return null;
        }

        UserDO userDO = new UserDO();
        userDO.setCorpId(corpId);
        userDO.setEnabled(Boolean.TRUE);
        userDO.setExists(Boolean.TRUE);
        userDO.setCodes(roleCode);

        int count = count(userDO);

        if (count == 0) {
            return null;
        }

        userDO.setPageNo(1);
        userDO.setPageSize(count);

        return BeanUtil.copy(list(userDO), User.class);
    }

    @Override
    public UserRole insertUserRole(@NotNull BigInteger userId, @NotNull BigInteger roleId,
                                   @NotBlank String creator) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);

        UserRoleDO userRoleDO = BeanUtil.copy(userRole, UserRoleDO.class);
        userRoleDO.setCreator(creator);

        try {
            userRoleMapper.insert(userRoleDO);
        } catch (Exception e) {
            logger.error(userRoleDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        userRole.setId(userRoleDO.getId());

        return userRole;
    }

    private List<UserRole> insertUserRole(BigInteger userId, List<UserRole> userRoleList,
                                          String creator) {
        if (userRoleList == null || userRoleList.isEmpty()) {
            return null;
        }

        for (UserRole userRole : userRoleList) {
            userRole.setUserId(userId);

            UserRoleDO userRoleDO = BeanUtil.copy(userRole, UserRoleDO.class);
            userRoleDO.setCreator(creator);

            try {
                userRoleMapper.insert(userRoleDO);
            } catch (Exception e) {
                logger.error(userRoleDO.toString(), e);
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
            }

            userRole.setId(userRoleDO.getId());
        }

        return userRoleList;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<UserRole> updateUserRole(@NotNull BigInteger corpId, BigInteger userId,
                                         String[] roleIds, @NotBlank String modifier) {
        userService.validate(corpId, userId);

        List<UserRole> userRoleList = new ArrayList<>();

        if (roleIds != null && roleIds.length > 0) {
            for (String roleId : roleIds) {
                // TODO roleService.validate(corpId, roleId);

                UserRole userRole = new UserRole();
                userRole.setRoleId(new BigInteger(roleId));

                userRoleList.add(userRole);
            }
        }

        List<UserRole> list0 = listUserRoles(userId);

        if (list0 == null || list0.isEmpty()) {
            insertUserRole(userId, userRoleList, modifier);

            userService.refreshToken(corpId, userId, modifier);

            return userRoleList;
        }

        Map<BigInteger, UserRole> map = new HashMap<>(list0.size());

        for (UserRole userRole : list0) {
            map.put(userRole.getRoleId(), userRole);
        }

        List<UserRole> list1 = new ArrayList<>();

        for (UserRole userRole : userRoleList) {
            if (map.containsKey(userRole.getRoleId())) {
                map.remove(userRole.getRoleId());
            } else {
                list1.add(userRole);
            }
        }

        insertUserRole(userId, list1, modifier);

        for (Map.Entry<BigInteger, UserRole> m : map.entrySet()) {
            UserRoleDO userRoleDO = BeanUtil.copy(m.getValue(), UserRoleDO.class);
            userRoleDO.setModifier(modifier);

            try {
                userRoleMapper.delete(userRoleDO);
            } catch (Exception e) {
                logger.error(userRoleDO.toString(), e);
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
            }
        }

        userService.refreshToken(corpId, userId, modifier);

        return userRoleList;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<UserRole> updateUserRole(@NotNull BigInteger corpId, @NotNull String[] userIds,
                                         BigInteger roleId, @NotBlank String modifier) {
        // TODO roleService.validate(corpId, roleId);

        List<UserRole> list = new ArrayList<>();

        for (String userId : userIds) {
            userService.validate(corpId, userId);

            UserRoleDO userRoleDO = new UserRoleDO();
            userRoleDO.setUserId(new BigInteger(userId));
            userRoleDO.setRoleId(roleId);
            userRoleDO.setCreator(modifier);

            try {
                userRoleMapper.insert(userRoleDO);
            } catch (Exception e) {
                logger.error(userRoleDO.toString(), e);
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
            }

            list.add(BeanUtil.copy(userRoleDO, UserRole.class));

            userService.refreshToken(corpId, new BigInteger(userId), modifier);
        }

        return list;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public UserRole deleteUserRole(@NotNull BigInteger corpId, BigInteger userId, BigInteger roleId,
                                   @NotBlank String modifier) {
        userService.validate(corpId, userId);
        // TODO roleService.validate(corpId, roleId);

        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setUserId(userId);
        userRoleDO.setRoleId(roleId);
        userRoleDO.setModifier(modifier);

        try {
            userRoleMapper.delete(userRoleDO);
        } catch (Exception e) {
            logger.error(userRoleDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        userService.refreshToken(corpId, userId, modifier);

        return BeanUtil.copy(userRoleDO, UserRole.class);
    }

    @Override
    public UserRole deleteUserRole(@NotNull BigInteger corpId, @NotNull String[] userIds,
                                   BigInteger roleId, @NotBlank String modifier) {
        // TODO roleService.validate(corpId, roleId);

        List<BigInteger> list = new ArrayList<>();

        for (String userId : userIds) {
            userService.validate(corpId, userId);

            list.add(new BigInteger(userId));
        }

        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setRoleId(roleId);
        userRoleDO.setUserIds(list.toArray(new BigInteger[0]));
        userRoleDO.setModifier(modifier);

        try {
            userRoleMapper.delete(userRoleDO);
        } catch (Exception e) {
            logger.error(userRoleDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        userService.refreshToken(corpId, list.toArray(new BigInteger[0]), modifier);

        return BeanUtil.copy(userRoleDO, UserRole.class);
    }

    private int count(UserRoleDO userRoleDO) {
        try {
            return userRoleMapper.count(userRoleDO);
        } catch (Exception e) {
            logger.error(userRoleDO.toString(), e);
        }

        return 0;
    }

    private List<UserRoleDO> list(UserRoleDO userRoleDO) {
        try {
            return userRoleMapper.list(userRoleDO);
        } catch (Exception e) {
            logger.error(userRoleDO.toString(), e);
        }

        return null;
    }

    private int count(UserDO userDO) {
        try {
            return userRoleMapper.countUser(userDO);
        } catch (Exception e) {
            logger.error(userDO.toString(), e);
        }

        return 0;
    }

    private List<UserDO> list(UserDO userDO) {
        try {
            return userRoleMapper.listUsers(userDO);
        } catch (Exception e) {
            logger.error(userDO.toString(), e);
        }

        return null;
    }

}
