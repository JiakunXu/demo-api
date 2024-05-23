package com.example.demo.user.service;

import com.example.demo.framework.annotation.NotBlank;
import com.example.demo.framework.annotation.NotNull;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.security.api.RefreshTokenService;
import com.example.demo.security.api.bo.LoginUser;
import com.example.demo.user.api.UserService;
import com.example.demo.user.api.bo.User;
import com.example.demo.user.dao.dataobject.UserDO;
import com.example.demo.user.dao.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author JiakunXu
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserMapper          userMapper;

    @Override
    public void validate(BigInteger corpId, String id) {

    }

    @Override
    public void validate(BigInteger corpId, BigInteger id) {

    }

    @Override
    public User getUser(BigInteger id) {
        return null;
    }

    @Override
    public User getUser(BigInteger corpId, String id) {
        return null;
    }

    @Override
    public User getUser(BigInteger corpId, BigInteger id) {
        return null;
    }

    @Override
    public LoginUser getUser(String username) {
        return null;
    }

    @Override
    public User refreshToken(@NotNull BigInteger corpId, @NotNull BigInteger id,
                             @NotBlank String modifier) {
        User before = getUser(corpId, id);

        if (before == null) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
        }

        UserDO userDO = new UserDO();
        userDO.setId(id);
        userDO.setCorpId(corpId);
        userDO.setModifier(modifier);

        try {
            if (userMapper.refreshToken(userDO) != 1) {
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            logger.error(userDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        refreshTokenService.remove(before);

        return BeanUtil.copy(userDO, User.class);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<User> refreshToken(@NotNull BigInteger corpId, @NotNull BigInteger[] ids,
                                   @NotBlank String modifier) {
        List<User> list = new ArrayList<>();

        for (BigInteger id : ids) {
            list.add(refreshToken(corpId, id, modifier));
        }

        return list;
    }

    private int count(UserDO userDO) {
        try {
            return userMapper.count(userDO);
        } catch (Exception e) {
            logger.error(userDO.toString(), e);
        }

        return 0;
    }

    private List<UserDO> list(UserDO userDO) {
        try {
            return userMapper.list(userDO);
        } catch (Exception e) {
            logger.error(userDO.toString(), e);
        }

        return null;
    }

    private UserDO get(UserDO userDO) {
        try {
            return userMapper.get(userDO);
        } catch (Exception e) {
            logger.error(userDO.toString(), e);
        }

        return null;
    }

}
