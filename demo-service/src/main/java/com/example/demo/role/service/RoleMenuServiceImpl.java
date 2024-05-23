package com.example.demo.role.service;

import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.role.api.RoleMenuService;
import com.example.demo.role.api.RoleService;
import com.example.demo.role.api.bo.RoleMenu;
import com.example.demo.role.dao.dataobject.RoleMenuDO;
import com.example.demo.role.dao.mapper.RoleMenuMapper;
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
public class RoleMenuServiceImpl implements RoleMenuService {

    private static final Logger logger = LoggerFactory.getLogger(RoleMenuServiceImpl.class);

    @Autowired
    private RoleService         roleService;

    @Autowired
    private RoleMenuMapper      roleMenuMapper;

    @Override
    public int countRoleMenu(BigInteger menuId) {
        if (menuId == null) {
            return 0;
        }

        RoleMenuDO roleMenuDO = new RoleMenuDO();
        roleMenuDO.setMenuId(menuId);

        return count(roleMenuDO);
    }

    @Override
    public List<RoleMenu> listRoleMenus(BigInteger roleId) {
        if (roleId == null) {
            return null;
        }

        RoleMenuDO roleMenuDO = new RoleMenuDO();
        roleMenuDO.setRoleId(roleId);

        return BeanUtil.copy(list(roleMenuDO), RoleMenu.class);
    }

    @Override
    public List<BigInteger> listRoleMenus(String roleId) {
        if (StringUtils.isBlank(roleId)) {
            return null;
        }

        List<RoleMenu> roleMenuList = listRoleMenus(new BigInteger(roleId));

        if (roleMenuList == null || roleMenuList.isEmpty()) {
            return null;
        }

        List<BigInteger> list = new ArrayList<>();

        for (RoleMenu roleMenu : roleMenuList) {
            list.add(roleMenu.getMenuId());
        }

        return list;
    }

    private List<RoleMenu> insertRoleMenu(BigInteger roleId, List<RoleMenu> roleMenuList,
                                          String creator) {
        if (roleMenuList == null || roleMenuList.isEmpty()) {
            return null;
        }

        for (RoleMenu roleMenu : roleMenuList) {
            roleMenu.setRoleId(roleId);

            RoleMenuDO roleMenuDO = BeanUtil.copy(roleMenu, RoleMenuDO.class);
            roleMenuDO.setCreator(creator);

            try {
                roleMenuMapper.insert(roleMenuDO);
            } catch (Exception e) {
                logger.error(roleMenuDO.toString(), e);
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
            }

            roleMenu.setId(roleMenuDO.getId());
        }

        return roleMenuList;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<RoleMenu> updateRoleMenu(BigInteger roleId, BigInteger[] menuIds, String modifier) {
        if (roleId == null || StringUtils.isBlank(modifier)) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "参数信息不能为空");
        }

        List<RoleMenu> roleMenuList = new ArrayList<>();

        if (menuIds != null && menuIds.length > 0) {
            for (BigInteger menuId : menuIds) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setMenuId(menuId);

                roleMenuList.add(roleMenu);
            }
        }

        List<RoleMenu> list0 = listRoleMenus(roleId);

        if (list0 == null || list0.isEmpty()) {
            return insertRoleMenu(roleId, roleMenuList, modifier);
        }

        Map<BigInteger, RoleMenu> map = new HashMap<>(list0.size());

        for (RoleMenu roleMenu : list0) {
            map.put(roleMenu.getMenuId(), roleMenu);
        }

        List<RoleMenu> list1 = new ArrayList<>();

        for (RoleMenu roleMenu : roleMenuList) {
            if (map.containsKey(roleMenu.getMenuId())) {
                map.remove(roleMenu.getMenuId());
            } else {
                list1.add(roleMenu);
            }
        }

        insertRoleMenu(roleId, list1, modifier);

        for (Map.Entry<BigInteger, RoleMenu> m : map.entrySet()) {
            RoleMenuDO roleMenuDO = BeanUtil.copy(m.getValue(), RoleMenuDO.class);
            roleMenuDO.setModifier(modifier);

            try {
                roleMenuMapper.delete(roleMenuDO);
            } catch (Exception e) {
                logger.error(roleMenuDO.toString(), e);
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
            }
        }

        return roleMenuList;
    }

    @Override
    public RoleMenu deleteRoleMenu(BigInteger roleId, BigInteger menuId, String modifier) {
        if ((roleId == null && menuId == null) || StringUtils.isBlank(modifier)) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "参数信息不能为空");
        }

        RoleMenuDO roleMenuDO = new RoleMenuDO();
        roleMenuDO.setRoleId(roleId);
        roleMenuDO.setMenuId(menuId);
        roleMenuDO.setModifier(modifier);

        try {
            roleMenuMapper.delete(roleMenuDO);
        } catch (Exception e) {
            logger.error(roleMenuDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        return BeanUtil.copy(roleMenuDO, RoleMenu.class);
    }

    private int count(RoleMenuDO roleMenuDO) {
        try {
            return roleMenuMapper.count(roleMenuDO);
        } catch (Exception e) {
            logger.error(roleMenuDO.toString(), e);
        }

        return 0;
    }

    private List<RoleMenuDO> list(RoleMenuDO roleMenuDO) {
        try {
            return roleMenuMapper.list(roleMenuDO);
        } catch (Exception e) {
            logger.error(roleMenuDO.toString(), e);
        }

        return null;
    }

}
