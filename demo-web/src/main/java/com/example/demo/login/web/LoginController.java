package com.example.demo.login.web;

import com.alibaba.fastjson2.JSONObject;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.response.TokenResponse;
import com.example.demo.framework.web.BaseController;
import com.example.demo.login.api.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/")
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public TokenResponse<String> login(HttpServletRequest request, HttpServletResponse response) {
        JSONObject data = this.getParameter(request, JSONObject.class);

        if (data == null) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "账号或密码不能为空");
        }

        return new TokenResponse<>(
            loginService.login(data.getString("username"), data.getString("password")));
    }

}
