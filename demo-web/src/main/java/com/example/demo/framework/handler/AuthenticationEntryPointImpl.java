package com.example.demo.framework.handler;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;

import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.response.ExceptionResponse;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException,
                                                                ServletException {
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(
            JSON.toJSONString(new ExceptionResponse(Constants.UNAUTHORIZED, "invalid token")));
    }

}
