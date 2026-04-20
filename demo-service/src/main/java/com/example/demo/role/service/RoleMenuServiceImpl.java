package com.example.demo.role.service;

import com.example.demo.framework.annotation.NotBlank;
import com.example.demo.framework.annotation.NotNull;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.service.impl.ServiceImpl;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.role.api.RoleMenuService;
import com.example.demo.role.api.RoleService;
import com.example.demo.role.api.bo.RoleMenu;
import com.example.demo.role.dao.dataobject.RoleMenuDO;
import com.example.demo.role.dao.mapper.RoleMenuMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenuDO>
                                 implements RoleMenuService {

    @Autowired
    private RoleService roleService;

    @Override
    public int countRoleMenu(BigInteger menuId) {
        if (menuId == null) {
            return 0;
        }

        RoleMenuDO roleMenuDO = new RoleMenuDO();
        roleMenuDO.setMenuId(menuId);

        return this.count(roleMenuDO);
    }

    @Override
    public List<RoleMenu> listRoleMenus(BigInteger roleId) {
        if (roleId == null) {
            return null;
        }

        RoleMenuDO roleMenuDO = new RoleMenuDO();
        roleMenuDO.setRoleId(roleId);

        return BeanUtil.copy(this.list(roleMenuDO), RoleMenu.class);
    }

    @Override
    public List<BigInteger> listRoleMenus(String roleId) {
        if (StringUtils.isBlank(roleId)) {
            return null;
        }

        List<RoleMenu> roleMenus = listRoleMenus(new BigInteger(roleId));

        if (CollectionUtils.isEmpty(roleMenus)) {
            return null;
        }

        List<BigInteger> list = new ArrayList<>();

        for (RoleMenu roleMenu : roleMenus) {
            list.add(roleMenu.getMenuId());
        }

        return list;
    }

    private List<RoleMenu> insertRoleMenus(BigInteger roleId, List<RoleMenu> roleMenus,
                                           String creator) {
        if (CollectionUtils.isEmpty(roleMenus)) {
            return null;
        }

        List<RoleMenuDO> roleMenuDOs = new ArrayList<>();

        for (RoleMenu roleMenu : roleMenus) {
            roleMenu.setRoleId(roleId);

            RoleMenuDO roleMenuDO = BeanUtil.copy(roleMenu, RoleMenuDO.class);
            roleMenuDO.setCreator(creator);

            roleMenuDOs.add(roleMenuDO);
        }

        try {
            this.insertBatch(roleMenuDOs);
        } catch (Exception e) {
            log.error("{}", roleMenuDOs, e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        return BeanUtil.copy(roleMenuDOs, RoleMenu.class);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public List<RoleMenu> updateRoleMenus(@NotNull BigInteger roleId, BigInteger[] menuIds,
                                          @NotBlank String modifier) {
        List<RoleMenu> roleMenuList = new ArrayList<>();

        if (menuIds != null) {
            for (BigInteger menuId : menuIds) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setMenuId(menuId);

                roleMenuList.add(roleMenu);
            }
        }

        List<RoleMenu> list0 = listRoleMenus(roleId);

        if (CollectionUtils.isEmpty(list0)) {
            return insertRoleMenus(roleId, roleMenuList, modifier);
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

        insertRoleMenus(roleId, list1, modifier);

        if (!map.isEmpty()) {
            RoleMenuDO roleMenuDO = new RoleMenuDO();
            roleMenuDO.setIds(map.values().stream().map(RoleMenu::getId).toList());
            roleMenuDO.setModifier(modifier);

            try {
                this.delete(roleMenuDO);
            } catch (Exception e) {
                log.error("{}", roleMenuDO, e);
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
            }
        }

        return roleMenuList;
    }

    @Override
    public RoleMenu deleteRoleMenu(BigInteger roleId, BigInteger menuId,
                                   @NotBlank String modifier) {
        if (roleId == null && menuId == null) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "参数信息不能为空");
        }

        RoleMenuDO roleMenuDO = new RoleMenuDO();
        roleMenuDO.setRoleId(roleId);
        roleMenuDO.setMenuId(menuId);
        roleMenuDO.setModifier(modifier);

        try {
            this.delete(roleMenuDO);
        } catch (Exception e) {
            log.error("{}", roleMenuDO, e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        return BeanUtil.copy(roleMenuDO, RoleMenu.class);
    }

}