package com.example.demo.framework.interceptor;

import com.example.demo.cache.api.RedisService;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.util.ThreadLocalUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author JiakunXu
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService<String, Object> redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String skey = request.getHeader("X-WX-Skey");

        if (StringUtils.isBlank(skey)) {
            throw new ServiceException(Constants.UNAUTHORIZED, "无效的访问令牌");
        }

        Object user;

        try {
            user = redisService.get(skey);
        } catch (ServiceException e) {
            throw new ServiceException(Constants.UNAUTHORIZED, "访问令牌已过期");
        }

        if (user == null) {
            throw new ServiceException(Constants.UNAUTHORIZED, "访问令牌已过期");
        }

        ThreadLocalUtil.setValue(user);

        return true;
    }

}
