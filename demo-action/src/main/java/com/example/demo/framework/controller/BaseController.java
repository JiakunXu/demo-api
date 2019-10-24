package com.example.demo.framework.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.api.user.bo.User;
import com.example.demo.framework.util.ThreadLocalUtil;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author JiakunXu
 */
@RestController
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    public User getUser() {
        return (User) ThreadLocalUtil.getValue();
    }

    public String getParameter(HttpServletRequest request, String name) {
        return request.getParameter(name);
    }

    public <T> T getParameter(HttpServletRequest request, Class<T> clazz) {
        if (!"application/json".equals(request.getContentType())) {
            return null;
        }

        String parameter = null;

        try {
            parameter = IOUtils.toString(request.getInputStream(), "UTF-8");
        } catch (IOException e) {
            logger.error("getParameter", e);
        }

        return JSON.parseObject(parameter, clazz);
    }

}
