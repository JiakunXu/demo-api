package com.example.demo.role.web;

import com.example.demo.framework.response.ObjectResponse;
import com.example.demo.framework.web.BaseController;
import com.example.demo.menu.api.MenuService;
import com.example.demo.role.api.RoleMenuService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/system/role/menu")
public class RoleMenuController extends BaseController {

    @Autowired
    private MenuService     menuService;

    @Autowired
    private RoleMenuService roleMenuService;

    @PreAuthorize("hasAuthority('role:crud')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ObjectResponse<Map<String, Object>> list(HttpServletRequest request,
                                                    HttpServletResponse response) {
        String roleId = this.getParameter(request, "roleId");

        Map<String, Object> map = new HashMap<>();
        map.put("checkedKeys", roleMenuService.listRoleMenus(this.getUser().getCorpId(), roleId));
        map.put("menus", menuService.listMenus());

        return new ObjectResponse<>(map);
    }

}
