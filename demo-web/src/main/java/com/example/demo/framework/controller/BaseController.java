package com.example.demo.framework.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.api.user.bo.User;
import com.example.demo.framework.bo.BaseParameter;
import com.example.demo.framework.util.ThreadLocalUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

    public <T extends BaseParameter> T getParameter(HttpServletRequest request, T parameter) {
        parameter.setSearch(request.getParameter("search"));
        parameter.setGmtStart(request.getParameter("gmtStart"));
        parameter.setGmtEnd(request.getParameter("gmtEnd"));
        parameter.setSort(request.getParameter("sort"));
        parameter.setDir(request.getParameter("dir"));

        String pageNo = request.getParameter("pageNo");
        if (StringUtils.isNotBlank(pageNo)) {
            parameter.setPageNo(Integer.parseInt(pageNo.trim()));
        }

        String pageSize = request.getParameter("pageSize");
        if (StringUtils.isNotBlank(pageSize)) {
            parameter.setPageSize(Integer.parseInt(pageSize.trim()));
        }

        return parameter;
    }

    public <T> T getParameter(HttpServletRequest request, Class<T> clazz) {
        if (!StringUtils.contains(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
            return null;
        }

        String parameter = null;

        try {
            parameter = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("getParameter", e);
        }

        return JSON.parseObject(parameter, clazz);
    }

    public <T> List<T> getParameterList(HttpServletRequest request, Class<T> clazz) {
        if (!StringUtils.contains(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
            return null;
        }

        String parameter = null;

        try {
            parameter = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("getParameter", e);
        }

        return JSON.parseArray(parameter, clazz);
    }

    public String getParameter(HttpServletRequest request) {
        try {
            return IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("getParameter", e);
        }

        return null;
    }

    public String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        return StringUtils.isNotEmpty(ip) ? ip : request.getRemoteAddr();
    }

}
