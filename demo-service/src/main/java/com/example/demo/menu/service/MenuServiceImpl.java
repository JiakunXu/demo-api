package com.example.demo.menu.service;

import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.menu.api.MenuService;
import com.example.demo.menu.api.bo.Menu;
import com.example.demo.menu.dao.dataobject.MenuDO;
import com.example.demo.menu.dao.mapper.MenuMapper;
import com.example.demo.role.api.RoleMenuService;
import com.example.demo.tree.api.bo.Tree;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    private RoleMenuService     roleMenuService;

    @Autowired
    private MenuMapper          menuMapper;

    @Override
    public int countMenu(BigInteger pid) {
        if (pid == null) {
            return 0;
        }

        MenuDO menuDO = new MenuDO();
        menuDO.setPid(pid);

        return count(menuDO);
    }

    @Override
    public int countMenu(String pid, Menu menu) {
        if (menu == null) {
            return 0;
        }

        if (StringUtils.isNotBlank(pid)) {
            menu.setPid(new BigInteger(pid));
        }

        return count(BeanUtil.copy(menu, MenuDO.class));
    }

    @Override
    public List<Menu> listMenus(String pid, Menu menu) {
        if (menu == null) {
            return null;
        }

        if (StringUtils.isNotBlank(pid)) {
            menu.setPid(new BigInteger(pid));
        }

        return BeanUtil.copy(list(BeanUtil.copy(menu, MenuDO.class)), Menu.class);
    }

    @Override
    public List<Menu> listMenus(String name, String status) {
        MenuDO menuDO = new MenuDO();
        menuDO.setName(name);
        menuDO.setStatus(status);

        return BeanUtil.copy(list(menuDO), Menu.class);
    }

    @Override
    public List<Tree> listMenus() {
        return listMenus((List<BigInteger>) null);
    }

    @Override
    public List<Tree> listMenus(List<BigInteger> ids) {
        MenuDO menuDO = new MenuDO();
        menuDO.setIds(ids);

        List<Menu> menuList = BeanUtil.copy(list(menuDO), Menu.class);

        if (menuList == null || menuList.isEmpty()) {
            return null;
        }

        return getTreeList(BigInteger.ZERO, menuList);
    }

    private List<Tree> getTreeList(BigInteger pid, List<Menu> menuList) {
        List<Tree> list = new ArrayList<>();

        for (Menu menu : menuList) {
            if (pid.compareTo(menu.getPid()) == 0) {
                Tree tree = new Tree(menu.getId(), menu.getName());

                tree.setChildren(getTreeList(menu.getId(), menuList));

                list.add(tree);
            }
        }

        return list;
    }

    @Override
    public List<Menu> listMenus(String[] type) {
        if (type == null || type.length == 0) {
            return null;
        }

        MenuDO menuDO = new MenuDO();
        menuDO.setStatus(Menu.Status.ENABLE.value);
        menuDO.setTypes(type);

        return BeanUtil.copy(list(menuDO), Menu.class);
    }

    @Override
    public List<Menu> listMenus(String[] type, BigInteger userId) {
        if (type == null || type.length == 0 || userId == null) {
            return null;
        }

        MenuDO menuDO = new MenuDO();
        menuDO.setStatus(Menu.Status.ENABLE.value);
        menuDO.setTypes(type);
        menuDO.setUserId(userId);

        return BeanUtil.copy(list(menuDO), Menu.class);
    }

    @Override
    public Menu getMenu(String id) {
        if (StringUtils.isBlank(id)) {
            return null;
        }

        MenuDO menuDO = new MenuDO();
        menuDO.setId(new BigInteger(id));

        return BeanUtil.copy(get(menuDO), Menu.class);
    }

    @Override
    public Menu insertMenu(BigInteger pid, Menu menu, String creator) {
        if (pid == null || menu == null || StringUtils.isBlank(creator)) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "参数信息不能为空");
        }

        menu.setPid(pid);

        MenuDO menuDO = BeanUtil.copy(menu, MenuDO.class);
        menuDO.setCreator(creator);

        try {
            menuMapper.insert(menuDO);
        } catch (Exception e) {
            logger.error(menuDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        menu.setId(menuDO.getId());

        return menu;
    }

    @Override
    public Menu updateMenu(BigInteger id, Menu menu, String modifier) {
        if (id == null || menu == null || StringUtils.isBlank(modifier)) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "参数信息不能为空");
        }

        menu.setId(id);

        MenuDO menuDO = BeanUtil.copy(menu, MenuDO.class);
        menuDO.setModifier(modifier);

        try {
            if (menuMapper.update(menuDO) != 1) {
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "暂无权限");
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            logger.error(menuDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        return menu;
    }

    @Override
    public Menu deleteMenu(BigInteger id, String modifier) {
        if (id == null || StringUtils.isBlank(modifier)) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "参数信息不能为空");
        }

        if (countMenu(id) > 0) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "已包含菜单，请先删除下级菜单");
        }

        if (roleMenuService.countRoleMenu(id) > 0) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "已关联角色，请先调整角色菜单");
        }

        MenuDO menuDO = new MenuDO();
        menuDO.setId(id);
        menuDO.setModifier(modifier);

        try {
            menuMapper.delete(menuDO);
        } catch (Exception e) {
            logger.error(menuDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息更新失败，请稍后再试");
        }

        return BeanUtil.copy(menuDO, Menu.class);
    }

    private int count(MenuDO menuDO) {
        try {
            return menuMapper.count(menuDO);
        } catch (Exception e) {
            logger.error(menuDO.toString(), e);
        }

        return 0;
    }

    private List<MenuDO> list(MenuDO menuDO) {
        try {
            return menuMapper.list(menuDO);
        } catch (Exception e) {
            logger.error(menuDO.toString(), e);
        }

        return null;
    }

    private MenuDO get(MenuDO menuDO) {
        try {
            return menuMapper.get(menuDO);
        } catch (Exception e) {
            logger.error(menuDO.toString(), e);
        }

        return null;
    }

}
