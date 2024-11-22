package com.example.demo.framework.web;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.bo.BaseBO;
import com.example.demo.security.api.bo.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author JiakunXu
 */
@RestController
public class BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    public LoginUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return "anonymousUser".equals(authentication.getName()) ? new LoginUser()
            : (LoginUser) authentication.getPrincipal();
    }

    public String getParameter(HttpServletRequest request, String name) {
        return request.getParameter(name);
    }

    public <T extends BaseBO> T getParameter(HttpServletRequest request, T parameter) {
        parameter.setSearch(request.getParameter("search"));
        parameter.setStart(request.getParameter("startDate"));
        parameter.setEnd(request.getParameter("endDate"));
        parameter.setSort(request.getParameter("sort"));

        String dir = request.getParameter("dir");
        if ("ascending".equals(dir)) {
            parameter.setDir("ASC");
        } else if ("descending".equals(dir)) {
            parameter.setDir("DESC");
        } else {
            parameter.setDir(dir);
        }

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

        try {
            return JSON.parseObject(
                IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8), clazz);
        } catch (IOException e) {
            logger.error("getParameter", e);
        }

        return JSON.parseObject("{}", clazz);
    }

    public <T> List<T> getParameters(HttpServletRequest request, Class<T> clazz) {
        if (!StringUtils.contains(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
            return null;
        }

        try {
            return JSON.parseArray(
                IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8), clazz);
        } catch (IOException e) {
            logger.error("getParameter", e);
        }

        return JSON.parseArray("[]", clazz);
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
