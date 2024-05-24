package com.example.demo.login.service;

import com.alibaba.fastjson2.JSON;
import com.example.demo.aliyun.api.ProducerService;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.log.api.bo.LoginLog;
import com.example.demo.login.api.LoginService;
import com.example.demo.security.api.TokenService;
import com.example.demo.security.api.bo.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ProducerService       producerService;

    @Autowired
    private TokenService          tokenService;

    @Override
    public String login(String username, String password) {
        if (StringUtils.isAnyBlank(username, password)) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "账号或密码不能为空");
        }

        try {
            Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

            handle(username, LoginLog.Status.SUCCESS.value, null);

            return tokenService.setUser((LoginUser) authentication.getPrincipal());
        } catch (Exception e) {
            handle(username, LoginLog.Status.FAIL.value, e.getMessage());

            if (e instanceof LockedException) {
                throw new LockedException("账号已锁定");
            } else if (e instanceof DisabledException) {
                throw new DisabledException("账号不可用");
            } else if (e instanceof AccountExpiredException) {
                throw new AccountExpiredException("账号已过期");
            } else if (e instanceof BadCredentialsException) {
                throw new BadCredentialsException("账号名或登录密码不正确");
            } else if (e instanceof CredentialsExpiredException) {
                throw new CredentialsExpiredException("密码已过期");
            } else if (e instanceof UsernameNotFoundException) {
                throw new UsernameNotFoundException("账号不存在");
            } else {
                throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "系统正忙，请稍后再试");
            }
        }
    }

    private void handle(String username, String status, String errMsg) {
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes();
        HttpServletRequest request = attributes == null ? null : attributes.getRequest();

        loginLog.setIp(request == null ? null : getRemoteAddr(request));
        loginLog.setIpAddr(null);
        loginLog.setLoginTime(new Date());
        loginLog.setStatus(status);
        loginLog.setErrMsg(errMsg);

        producerService.send("topic", "login.log.save", JSON.toJSONBytes(loginLog), username);
    }

    private String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        return StringUtils.isNotEmpty(ip) ? ip : request.getRemoteAddr();
    }

}
