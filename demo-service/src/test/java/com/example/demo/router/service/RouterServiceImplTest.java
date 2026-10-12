package com.example.demo.router.service;

import com.example.demo.menu.api.MenuService;
import com.example.demo.menu.api.bo.Menu;
import com.example.demo.role.api.RoleService;
import com.example.demo.router.api.bo.Router;
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

class RouterServiceImplTest {
    private final MenuService menus = mock(MenuService.class);
    private final RouterServiceImpl service = new RouterServiceImpl();
    @BeforeEach
    void initialize() { ReflectionTestUtils.setField(service, "menuService", menus); }
    private Menu menu(int id, int pid, String type, String path, String component) {
        Menu menu = new Menu(); menu.setId(BigInteger.valueOf(id)); menu.setPid(BigInteger.valueOf(pid));
        menu.setType(type);
        menu.setPath(path);
        menu.setComponent(component);
        menu.setName("title");
        menu.setIcon("icon");
        menu.setExternal(false); menu.setCached(false); menu.setHidden(false); return menu;
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void adminLoadsAllRoutesAndOrdinaryUserLoadsOnlyOwnRoutes(boolean admin) {
        Menu menu = menu(1, 0, Menu.Type.MENU.value, "home", "views/home");
        if (admin) when(menus.listMenus(any(String[].class))).thenReturn(List.of(menu));
        else when(menus.listMenus(any(String[].class), eq(BigInteger.TEN))).thenReturn(List.of(menu));
        List<Router> result = service.listRouters(BigInteger.TEN, admin ? List.of(new Permission(RoleService.ROLE_ADMIN)) : List.of());
        Router root = result.getFirst();
        assertThat(root.getPath()).isEqualTo("/");
        assertThat(root.getComponent()).isEqualTo("Layout");
        assertThat(root.getMeta()).isNull();
        Router child = root.getChildren().getFirst();
        assertThat(child.getPath()).isEqualTo("home"); assertThat(child.getName()).isEqualTo("ViewsHome");
        assertThat(child.getComponent()).isEqualTo("views/home");
        assertThat(child.getMeta().getTitle()).isEqualTo("title");
        if (admin) verify(menus, never()).listMenus(any(String[].class), any(BigInteger.class));
        else verify(menus, never()).listMenus(any(String[].class));
    }

    @Test
    void foldersNestedFoldersAndInternalLinksUseExpectedComponents() {
        Menu root = menu(1, 0, Menu.Type.CONTENTS.value, "system", null);
        Menu nested = menu(2, 1, Menu.Type.CONTENTS.value, "nested", null);
        Menu link = menu(3, 2, Menu.Type.MENU.value, "https://example.com", null);
        when(menus.listMenus(any(String[].class), any(BigInteger.class))).thenReturn(List.of(root, nested, link));
        Router route = service.listRouters(BigInteger.TEN, null).getFirst();
        assertThat(route.getPath()).isEqualTo("/system");
        assertThat(route.isAlwaysShow()).isTrue();
        assertThat(route.getRedirect()).isEqualTo("noRedirect");
        Router folder = route.getChildren().getFirst(); assertThat(folder.getComponent()).isEqualTo("ParentView");
        Router external = folder.getChildren().getFirst();
        assertThat(external.getPath()).isEqualTo("example.com");
        assertThat(external.getComponent()).isEqualTo("InnerLink");
        assertThat(external.getMeta().isNoCache()).isTrue();
    }

    @Test
    void externalRootLinkRetainsUrlAndExplicitComponent() {
        Menu link = menu(1, 0, Menu.Type.MENU.value, "https://example.com", "ExternalView"); link.setExternal(true); link.setCached(true); link.setHidden(true);
        when(menus.listMenus(any(String[].class), any(BigInteger.class))).thenReturn(List.of(link));
        Router route = service.listRouters(BigInteger.TEN, List.of()).getFirst();
        assertThat(route.getPath()).isEqualTo("https://example.com"); assertThat(route.getComponent()).isEqualTo("ExternalView");
        assertThat(route.isHidden()).isTrue(); assertThat(route.getMeta().isNoCache()).isFalse();
    }

    @Test
    void missingUserOrMenusHasNoRoutes() {
        assertThat(service.listRouters(null, List.of())).isNull(); verifyNoInteractions(menus);
        assertThat(service.listRouters(BigInteger.TEN, List.of())).isNull();
    }
}
