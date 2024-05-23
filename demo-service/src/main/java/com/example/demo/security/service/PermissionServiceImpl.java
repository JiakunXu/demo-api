package com.example.demo.security.service;

import com.example.demo.framework.annotation.NotBlank;
import com.example.demo.framework.annotation.NotNull;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.menu.api.MenuService;
import com.example.demo.menu.api.bo.Menu;
import com.example.demo.role.api.RoleService;
import com.example.demo.role.api.bo.Role;
import com.example.demo.security.api.PermissionService;
import com.example.demo.security.api.bo.Permission;
import com.example.demo.user.api.UserRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private MenuService     menuService;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public void validate(@NotNull BigInteger userId, @NotBlank String authority) {
        List<Role> roleList = userRoleService.listRoles(userId, Role.Status.ENABLE.value);

        if (roleList == null || roleList.isEmpty()) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
        }

        for (Role role : roleList) {
            if (RoleService.ROLE_ADMIN.equals(role.getCode())) {
                return;
            }
        }

        List<Menu> menuList = menuService.listMenus(new String[] { Menu.Type.BUTTON.value },
            userId);

        if (menuList == null || menuList.isEmpty()) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
        }

        for (Menu menu : menuList) {
            if (authority.equals(menu.getCode())) {
                return;
            }
        }

        throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
    }

    @Override
    public List<Permission> listPermissions(BigInteger userId) {
        if (userId == null) {
            return null;
        }

        List<Permission> list = new ArrayList<>();

        boolean isAdmin = false;

        List<Role> roleList = userRoleService.listRoles(userId, Role.Status.ENABLE.value);

        if (roleList != null && !roleList.isEmpty()) {
            for (Role role : roleList) {
                if (RoleService.ROLE_ADMIN.equals(role.getCode())) {
                    isAdmin = true;
                }

                list.add(new Permission(role.getCode()));
            }
        }

        String[] type = new String[] { Menu.Type.MENU.value, Menu.Type.BUTTON.value };

        List<Menu> menuList = isAdmin ? menuService.listMenus(type)
            : menuService.listMenus(type, userId);

        if (menuList != null && !menuList.isEmpty()) {
            for (Menu menu : menuList) {
                if (StringUtils.isBlank(menu.getCode())) {
                    continue;
                }

                list.add(new Permission(menu.getCode()));
            }
        }

        return list;
    }

}
