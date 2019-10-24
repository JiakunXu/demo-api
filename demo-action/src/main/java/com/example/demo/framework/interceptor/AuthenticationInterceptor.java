package com.example.demo.framework.interceptor;

import com.example.demo.api.cache.MemcachedCacheService;
import com.example.demo.api.user.bo.User;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.util.ThreadLocalUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;

/**
 * @author JiakunXu
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private MemcachedCacheService memcachedCacheService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String token = request.getHeader("token");

        // test
        token = "0";
        User u = new User();
        u.setId(BigInteger.ONE);
        u.setName("name");
        memcachedCacheService.set(token, u);
        // test

        if (StringUtils.isBlank(token)) {
            throw new ServiceException("", "token is blank");
        }

        Object user = null;

        try {
            user = memcachedCacheService.get(token);
        } catch (ServiceException e) {
            throw new ServiceException("", "token is expire");
        }

        if (user == null) {
            throw new ServiceException("", "token is expire");
        }

        ThreadLocalUtil.setValue(user);

        return true;
    }

}
