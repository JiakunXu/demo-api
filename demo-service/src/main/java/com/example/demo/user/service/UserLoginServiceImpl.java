package com.example.demo.user.service;

import com.example.demo.framework.service.impl.ServiceImpl;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.security.api.PermissionService;
import com.example.demo.security.api.bo.LoginUser;
import com.example.demo.user.api.UserLoginService;
import com.example.demo.user.dao.dataobject.UserDO;
import com.example.demo.user.dao.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Slf4j
@Service
public class UserLoginServiceImpl extends ServiceImpl<UserMapper, UserDO>
                                  implements UserLoginService {

    @Autowired
    private PermissionService permissionService;

    @Override
    public LoginUser getUser(BigInteger id) {
        if (id == null) {
            return null;
        }

        LoginUser user = BeanUtil.copy(this.get(new UserDO(id)), LoginUser.class);

        if (user == null) {
            return null;
        }

        return setAuthorities(user);
    }

    @Override
    public LoginUser getUser(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }

        LoginUser user = BeanUtil.copy(this.get(new UserDO(username)), LoginUser.class);

        if (user == null) {
            return null;
        }

        return setAuthorities(user);
    }

    private LoginUser setAuthorities(LoginUser user) {
        if (!user.isEnabled()) {
            return user;
        }

        user.setAuthorities(permissionService.listPermissions(user.getId()));

        return user;
    }

}
