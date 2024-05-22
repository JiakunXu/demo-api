package com.example.demo.role.dao.mapper;

import com.example.demo.framework.mapper.BaseMapper;
import com.example.demo.role.dao.dataobject.RoleDO;

public interface RoleMapper extends BaseMapper<RoleDO> {

    int updateStatus(RoleDO roleDO);

}
