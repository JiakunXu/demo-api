package com.example.demo.login.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSONObject;

import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.response.TokenResponse;
import com.example.demo.framework.web.BaseController;
import com.example.demo.login.api.LoginService;

@RestController
@RequestMapping(value = "/")
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public TokenResponse<String> login(HttpServletRequest request, HttpServletResponse response) {
        JSONObject object = this.getParameter(request, JSONObject.class);

        if (object == null) {
            throw new ServiceException(Constants.MISSING_PARAMETER, "账号或密码不能为空");
        }

        return new TokenResponse<>(
            loginService.login(object.getString("username"), object.getString("password")));
    }

}
