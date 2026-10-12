package com.example.demo.login.service;

import com.alibaba.fastjson2.JSON;
import com.example.demo.login.api.bo.LoginLog;
import com.example.demo.mq.api.ProducerService;
import com.example.demo.security.api.TokenService;
import com.example.demo.security.api.bo.LoginUser;
import com.example.demo.framework.exception.ServiceException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.util.ReflectionTestUtils;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginServiceImplTest {
    private final AuthenticationManager authentication = mock(AuthenticationManager.class);
    private final ProducerService producer = mock(ProducerService.class);
    private final TokenService tokens = mock(TokenService.class);
    private final LoginServiceImpl service = new LoginServiceImpl();

    @BeforeEach
    void initialize() {
        ReflectionTestUtils.setField(service, "authenticationManager", authentication);
        ReflectionTestUtils.setField(service, "producerService", producer);
        ReflectionTestUtils.setField(service, "tokenService", tokens);
    }

    @AfterEach
    void cleanRequest() { RequestContextHolder.resetRequestAttributes(); }

    static Stream<Object[]> failureCases() {
        return Stream.of(
            new Object[] {new LockedException("locked"), LockedException.class, "账号已锁定"},
            new Object[] {new DisabledException("disabled"), DisabledException.class, "账号不可用"},
            new Object[] {new AccountExpiredException("expired"), AccountExpiredException.class, "账号已过期"},
            new Object[] {new BadCredentialsException("wrong"), BadCredentialsException.class, "账号名或登录密码不正确"},
            new Object[] {new CredentialsExpiredException("password expired"), CredentialsExpiredException.class, "密码已过期"},
            new Object[] {new UsernameNotFoundException("missing"), UsernameNotFoundException.class, "账号不存在"},
            new Object[] {new RuntimeException("offline"), ServiceException.class, "系统正忙，请稍后再试"});
    }

    @ParameterizedTest
    @MethodSource("failureCases")
    void failedAuthenticationRecordsFailureWithoutIssuingToken(RuntimeException failure, Class<? extends Throwable> type, String message) {
        when(authentication.authenticate(any())).thenThrow(failure);
        assertThatThrownBy(() -> service.login("alice", "password")).isInstanceOf(type).hasMessage(message);
        var payload = ArgumentCaptor.forClass(byte[].class);
        verify(producer).send(eq("topic"), eq("login.log.save"), payload.capture(), eq("alice"));
        LoginLog log = JSON.parseObject(payload.getValue(), LoginLog.class);
        assertThat(log.getStatus()).isEqualTo(LoginLog.Status.FAIL.value);
        assertThat(log.getErrMsg()).isEqualTo(failure.getMessage());
        assertThat(log.getUsername()).isEqualTo("alice");
        verifyNoInteractions(tokens);
    }

    @Test
    void successfulLoginRecordsForwardedIpAndIssuesTokenForPrincipal() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("X-Forwarded-For", "203.0.113.10");
        request.setRemoteAddr("127.0.0.1");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        LoginUser user = new LoginUser();
        Authentication result = mock(Authentication.class);
        when(result.getPrincipal()).thenReturn(user);
        when(authentication.authenticate(any())).thenReturn(result);
        when(tokens.setUser(user)).thenReturn("jwt");
        assertThat(service.login("alice", "password")).isEqualTo("jwt");
        var credentials = ArgumentCaptor.forClass(Authentication.class);
        verify(authentication).authenticate(credentials.capture());
        assertThat(credentials.getValue().getPrincipal()).isEqualTo("alice");
        assertThat(credentials.getValue().getCredentials()).isEqualTo("password");
        var payload = ArgumentCaptor.forClass(byte[].class);
        verify(producer).send(eq("topic"), eq("login.log.save"), payload.capture(), eq("alice"));
        LoginLog log = JSON.parseObject(payload.getValue(), LoginLog.class);
        assertThat(log.getStatus()).isEqualTo(LoginLog.Status.SUCCESS.value);
        assertThat(log.getIp()).isEqualTo("203.0.113.10");
        assertThat(log.getLoginTime()).isNotNull();
        assertThat(log.getErrMsg()).isNull();
    }

    @Test
    void missingCredentialsCannotAuthenticateOrPublishLog() {
        for (String[] pair : new String[][] {{null, "p"}, {" ", "p"}, {"user", null}, {"user", ""}}) {
            assertThatThrownBy(() -> service.login(pair[0], pair[1])).hasMessage("账号或密码不能为空");
        }
        verifyNoInteractions(authentication, producer, tokens);
    }

    @Test
    void requestWithoutForwardedHeaderUsesRemoteAddress() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("192.0.2.10");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        when(authentication.authenticate(any())).thenThrow(new BadCredentialsException("wrong"));
        assertThatThrownBy(() -> service.login("alice", "password")).isInstanceOf(BadCredentialsException.class);
        var payload = ArgumentCaptor.forClass(byte[].class);
        verify(producer).send(anyString(), anyString(), payload.capture(), anyString());
        assertThat(JSON.parseObject(payload.getValue(), LoginLog.class).getIp()).isEqualTo("192.0.2.10");
    }
}
