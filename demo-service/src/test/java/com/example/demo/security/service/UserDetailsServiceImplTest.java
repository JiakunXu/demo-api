package com.example.demo.security.service;

import com.example.demo.user.api.UserService;
import com.example.demo.security.api.bo.LoginUser;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {
    private final UserService users = mock(UserService.class);
    private final UserDetailsServiceImpl service = new UserDetailsServiceImpl();

    @BeforeEach
    void initialize() { ReflectionTestUtils.setField(service, "userService", users); }

    @ParameterizedTest
    @CsvSource({"true,true", "true,false", "false,true", "false,false"})
    void translatesExpiredAndLockedFlags(boolean expired, boolean locked) {
        LoginUser user = new LoginUser();
        user.setExpired(expired);
        user.setLocked(locked);
        when(users.getUser("alice")).thenReturn(user);
        assertThat(service.loadUserByUsername("alice")).isSameAs(user);
        assertThat(user.isAccountNonExpired()).isEqualTo(!expired);
        assertThat(user.isAccountNonLocked()).isEqualTo(!locked);
    }

    @Test
    void unknownUsernameIsRejected() {
        assertThatThrownBy(() -> service.loadUserByUsername("missing")).isInstanceOf(UsernameNotFoundException.class).hasMessage("missing");
    }
}
