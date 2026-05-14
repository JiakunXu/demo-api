package com.example.demo.framework.handler;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.constant.HttpStatus;
import com.example.demo.framework.response.ExceptionResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException,
                                                                ServletException {
        response.setStatus(org.springframework.http.HttpStatus.OK.value());
        response.getWriter().write(
            JSON.toJSONString(new ExceptionResponse(HttpStatus.UNAUTHORIZED, "invalid token")));
    }

}
