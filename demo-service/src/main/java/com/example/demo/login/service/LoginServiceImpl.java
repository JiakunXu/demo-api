package com.example.demo.login.service;

import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.login.api.LoginService;
import com.example.demo.security.api.TokenService;
import com.example.demo.security.api.bo.LoginUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService          tokenService;

    @Override
    public String login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new ServiceException(Constants.MISSING_PARAMETER, "账号或密码不能为空");
        }

        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        return tokenService.setUser((LoginUser) authentication.getPrincipal());
    }

}
