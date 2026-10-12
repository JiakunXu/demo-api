package com.example.demo.user.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.user.api.bo.User;
import com.example.demo.user.dao.dataobject.UserDO;
import com.example.demo.user.dao.mapper.UserMapper;
import com.example.demo.security.api.*;
import com.example.demo.security.api.bo.*;
import com.example.demo.framework.exception.ServiceException;
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

class UserServiceImplTest {
    private final BigInteger id = BigInteger.TEN;
    private final UserMapper mapper = mock(UserMapper.class);
    private final PermissionService permissions = mock(PermissionService.class);
    private final UserServiceImpl service = spy(new UserServiceImpl());
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

    @Test
    void corporateLookupAndValidationUseBothTenantAndUserIdentifiers() {
        UserDO row = new UserDO(); row.setId(id); row.setCorpId(BigInteger.ONE);
        doReturn(row).when(service).getOne(any(Wrapper.class));
        assertThat(service.getUser(BigInteger.ONE, "10").getId()).isEqualTo(id);
        service.validate(BigInteger.ONE, id); service.validate(BigInteger.ONE, "10");
        ArgumentCaptor<Wrapper<UserDO>> queries = ArgumentCaptor.captor();
        verify(service, times(3)).getOne(queries.capture());
        assertThat(queries.getValue().getSqlSegment()).contains("id", "corp_id");
        assertThat(((LambdaQueryWrapper<UserDO>) queries.getValue()).getParamNameValuePairs().values()).contains(id, BigInteger.ONE);
        doReturn(null).when(service).getOne(any(Wrapper.class));
        assertThatThrownBy(() -> service.validate(BigInteger.ONE, id)).hasMessage("暂无权限");
        assertThatThrownBy(() -> service.validate(BigInteger.ONE, "10")).hasMessage("暂无权限");
        assertThat(service.getUser(null, id)).isNull();
        assertThat(service.getUser(BigInteger.ONE, "")).isNull();
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void revisionRefreshScopesTenantAndInvalidatesOldRevisionOnlyAfterUpdate(boolean success) {
        RefreshTokenService refresh = mock(RefreshTokenService.class);
        ReflectionTestUtils.setField(service, "refreshTokenService", refresh);
        User before = new User(); before.setId(id); before.setRefreshToken("old");
        doReturn(before).when(service).getUser(BigInteger.ONE, id);
        doReturn(success).when(service).update(any(Wrapper.class));
        if (success) {
            User result = service.refreshToken(BigInteger.ONE, id, "editor");
            assertThat(result.getRefreshToken()).matches("[0-9a-f-]{36}");
            verify(refresh).remove(before);
        } else {
            assertThatThrownBy(() -> service.refreshToken(BigInteger.ONE, id, "editor")).isInstanceOf(ServiceException.class);
            verifyNoInteractions(refresh);
        }
        ArgumentCaptor<Wrapper<UserDO>> capture = ArgumentCaptor.captor();
        verify(service).update(capture.capture());
        assertThat(capture.getValue().getSqlSegment()).contains("id", "corp_id");
    }

    @Test
    void batchRefreshKeepsOrderAndRejectsUnknownTenantUser() {
        User first = new User(); first.setId(id); User second = new User(); second.setId(BigInteger.ONE);
        doReturn(first).when(service).refreshToken(BigInteger.ONE, id, "editor");
        doReturn(second).when(service).refreshToken(BigInteger.ONE, BigInteger.ONE, "editor");
        assertThat(service.refreshToken(BigInteger.ONE, new BigInteger[] {id, BigInteger.ONE}, "editor")).containsExactly(first, second);
        assertThatThrownBy(() -> service.refreshToken(BigInteger.TWO, id, "editor")).hasMessage("暂无权限");
    }

}
