package com.example.demo.security.service;

import com.example.demo.menu.api.MenuService;
import com.example.demo.menu.api.bo.Menu;
import com.example.demo.role.api.RoleService;
import com.example.demo.role.api.bo.Role;
import com.example.demo.user.api.UserRoleService;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.security.api.bo.Permission;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import java.math.BigInteger;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PermissionServiceImplTest {
    private final BigInteger userId = BigInteger.TEN;
    private final MenuService menus = mock(MenuService.class);
    private final UserRoleService roles = mock(UserRoleService.class);
    private final PermissionServiceImpl service = new PermissionServiceImpl();

    @BeforeEach
    void initialize() {
        ReflectionTestUtils.setField(service, "menuService", menus);
        ReflectionTestUtils.setField(service, "userRoleService", roles);
    }

    private Role role(String code) { Role role = new Role(); role.setCode(code); return role; }
    private Menu menu(String code) { Menu menu = new Menu(); menu.setCode(code); return menu; }

    @Test
    void administratorDoesNotNeedMenuPermission() {
        when(roles.listRoles(userId, Role.Status.ENABLE.value)).thenReturn(List.of(role("user"), role(RoleService.ROLE_ADMIN)));
        assertThatCode(() -> service.validate(userId, "user:delete")).doesNotThrowAnyException();
        verifyNoInteractions(menus);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void missingRolesRejectAccessWithoutLoadingMenus(boolean nullList) {
        when(roles.listRoles(userId, Role.Status.ENABLE.value)).thenReturn(nullList ? null : List.of());
        assertThatThrownBy(() -> service.validate(userId, "user:delete")).isInstanceOf(ServiceException.class).hasMessage("暂无权限");
        verifyNoInteractions(menus);
    }

    @Test
    void ordinaryUserRequiresMatchingButtonPermission() {
        when(roles.listRoles(userId, Role.Status.ENABLE.value)).thenReturn(List.of(role("user")));
        when(menus.listMenus(any(String[].class), eq(userId))).thenReturn(List.of(menu("user:read"), menu("user:delete")));
        assertThatCode(() -> service.validate(userId, "user:delete")).doesNotThrowAnyException();
        assertThatThrownBy(() -> service.validate(userId, "user:update")).hasMessage("暂无权限");
        verify(menus, times(2)).listMenus(org.mockito.AdditionalMatchers.aryEq(new String[] {Menu.Type.BUTTON.value}), eq(userId));
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void missingMenusRejectAccess(boolean nullList) {
        when(roles.listRoles(userId, Role.Status.ENABLE.value)).thenReturn(List.of(role("user")));
        when(menus.listMenus(any(String[].class), eq(userId))).thenReturn(nullList ? null : List.of());
        assertThatThrownBy(() -> service.validate(userId, "user:delete")).hasMessage("暂无权限");
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void listsRolesAndNonblankMenuCodesUsingCorrectScope(boolean admin) {
        String code = admin ? RoleService.ROLE_ADMIN : "user";
        when(roles.listRoles(userId, Role.Status.ENABLE.value)).thenReturn(List.of(role(code)));
        List<Menu> menuList = List.of(menu("user:read"), menu(""), menu("   "), menu(null));
        if (admin) when(menus.listMenus(any(String[].class))).thenReturn(menuList);
        else when(menus.listMenus(any(String[].class), eq(userId))).thenReturn(menuList);
        assertThat(service.listPermissions(userId)).extracting(Permission::getAuthority).containsExactly(code, "user:read");
        if (admin) verify(menus, never()).listMenus(any(String[].class), any(BigInteger.class));
        else verify(menus, never()).listMenus(any(String[].class));
    }

    @Test
    void nullUserHasNoDependenciesAndMissingPermissionsProduceEmptyList() {
        assertThat(service.listPermissions(null)).isNull();
        verifyNoInteractions(menus, roles);
        assertThat(service.listPermissions(userId)).isEmpty();
    }
}
