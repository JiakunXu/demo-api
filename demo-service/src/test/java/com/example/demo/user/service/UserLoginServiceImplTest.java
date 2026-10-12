package com.example.demo.user.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.user.dao.dataobject.UserDO;
import com.example.demo.user.dao.mapper.UserMapper;
import com.example.demo.security.api.*;
import com.example.demo.security.api.bo.*;
import com.example.demo.support.ServiceTestSupport;
import org.mockito.ArgumentCaptor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import java.math.BigInteger;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserLoginServiceImplTest {
    private final BigInteger id = BigInteger.TEN;
    private final UserMapper mapper = mock(UserMapper.class);
    private final PermissionService permissions = mock(PermissionService.class);
    private final UserLoginServiceImpl service = spy(new UserLoginServiceImpl());
    @BeforeEach
    void initialize() {
        ServiceTestSupport.mapper(service, mapper, UserDO.class);
        ReflectionTestUtils.setField(service, "permissionService", permissions);
    }

    @Test
    void missingIdentifiersAvoidQueries() {
        assertThat(service.getUser((BigInteger) null)).isNull();
        assertThat(service.getUser(" ")).isNull();
        verifyNoInteractions(mapper, permissions);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void loginMapsAccountFlagsAndLoadsAuthoritiesOnlyWhenEnabled(boolean enabled) {
        UserDO row = new UserDO(); row.setId(id); row.setUsername("alice"); row.setPassword("hash");
        row.setEnabled(enabled); row.setExpired(false); row.setLocked(true);
        doReturn(row).when(service).getOne(any(Wrapper.class));
        when(permissions.listPermissions(id)).thenReturn(List.of(new Permission("user:read")));
        LoginUser user = service.getUser("alice");
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getPassword()).isEqualTo("hash");
        assertThat(user.isEnabled()).isEqualTo(enabled);
        assertThat(user.isAccountNonExpired()).isTrue(); assertThat(user.isAccountNonLocked()).isFalse();
        if (enabled) assertThat(user.getAuthorities()).extracting(org.springframework.security.core.GrantedAuthority::getAuthority).containsExactly("user:read");
        else verifyNoInteractions(permissions);
        ArgumentCaptor<Wrapper<UserDO>> query = ArgumentCaptor.captor();
        verify(service).getOne(query.capture());
        assertThat(query.getValue().getSqlSegment()).contains("username");
        assertThat(((LambdaQueryWrapper<UserDO>) query.getValue()).getParamNameValuePairs().values()).contains("alice");
    }

    @Test
    void unknownLoginReturnsNull() {
        doReturn(null).when(service).getOne(any(Wrapper.class));
        assertThat(service.getUser("missing")).isNull();
        verifyNoInteractions(permissions);
    }

    @Test
    void numericLookupMapsStoredIdentifier() {
        UserDO row = new UserDO(); row.setId(id); row.setEnabled(false);
        doReturn(row).when(service).getById(id);
        assertThat(service.getUser(id).getId()).isEqualTo(id);
        doReturn(null).when(service).getById(id);
        assertThat(service.getUser(id)).isNull();
    }

}
