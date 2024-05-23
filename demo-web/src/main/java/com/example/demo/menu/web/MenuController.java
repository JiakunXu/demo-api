package com.example.demo.menu.web;

import com.example.demo.framework.annotation.Log;
import com.example.demo.framework.response.ListResponse;
import com.example.demo.framework.response.ObjectResponse;
import com.example.demo.framework.web.BaseController;
import com.example.demo.menu.api.MenuService;
import com.example.demo.menu.api.bo.Menu;
import com.example.demo.tree.api.bo.Tree;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/system/menu")
public class MenuController extends BaseController {

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public ListResponse<Tree> tree(HttpServletRequest request, HttpServletResponse response) {
        return new ListResponse<>(menuService.listMenus());
    }

    @PreAuthorize("hasAuthority('menu:crud')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ListResponse<Menu> list(HttpServletRequest request, HttpServletResponse response) {
        String name = this.getParameter(request, "name");
        String status = this.getParameter(request, "status");
        return new ListResponse<>(menuService.listMenus(name, status));
    }

    @PreAuthorize("hasAuthority('menu:crud')")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ObjectResponse<Menu> get(HttpServletRequest request, HttpServletResponse response) {
        String id = this.getParameter(request, "id");
        return new ObjectResponse<>(menuService.getMenu(id));
    }

    @Log(module = "")
    @PreAuthorize("hasAuthority('menu:crud')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ObjectResponse<Menu> save(HttpServletRequest request, HttpServletResponse response) {
        Menu menu = this.getParameter(request, Menu.class);
        return new ObjectResponse<>(
                menuService.insertMenu(menu.getPid(), menu, this.getUser().getName()));
    }

    @Log(module = "")
    @PreAuthorize("hasAuthority('menu:crud')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ObjectResponse<Menu> update(HttpServletRequest request, HttpServletResponse response) {
        Menu menu = this.getParameter(request, Menu.class);
        return new ObjectResponse<>(
                menuService.updateMenu(menu.getId(), menu, this.getUser().getName()));
    }

    @Log(module = "")
    @PreAuthorize("hasAuthority('menu:crud')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ObjectResponse<Menu> delete(HttpServletRequest request, HttpServletResponse response) {
        Menu menu = this.getParameter(request, Menu.class);
        return new ObjectResponse<>(menuService.deleteMenu(menu.getId(), this.getUser().getName()));
    }

}
