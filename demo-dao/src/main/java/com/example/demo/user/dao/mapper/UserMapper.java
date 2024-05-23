package com.example.demo.user.dao.mapper;

import com.example.demo.framework.mapper.BaseMapper;
import com.example.demo.user.dao.dataobject.UserDO;

/**
 * @author JiakunXu
 */
public interface UserMapper extends BaseMapper<UserDO> {

    int refreshToken(UserDO userDO);

}
