package com.example.demo.framework.aspectj;

import com.alibaba.fastjson2.JSON;
import com.example.demo.aliyun.api.ProducerService;
import com.example.demo.framework.annotation.Log;
import com.example.demo.log.api.bo.OperLog;
import com.example.demo.security.api.bo.LoginUser;
import com.example.demo.user.api.bo.User;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;
import java.util.UUID;

@Aspect
@Component
public class LogAspect {

    @Autowired
    private ProducerService producerService;

    @AfterReturning(pointcut = "@annotation(log)", returning = "object")
    public void doAfterReturning(JoinPoint joinPoint, Log log, Object object) {
        handle(joinPoint.getTarget().getClass().getSimpleName(), log.module(), log.desc(),
            OperLog.Status.SUCCESS.value, null);
    }

    @AfterThrowing(value = "@annotation(log)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log log, Exception e) {
        handle(joinPoint.getTarget().getClass().getSimpleName(), log.module(), log.desc(),
            OperLog.Status.FAIL.value, e.getMessage());
    }

    private void handle(String clazz, String module, String desc, String status, String errMsg) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = "anonymousUser".equals(authentication.getName()) ? new User("anonymous")
            : (LoginUser) authentication.getPrincipal();

        OperLog operLog = new OperLog();
        operLog.setClazz(clazz);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes();
        HttpServletRequest request = attributes == null ? null : attributes.getRequest();

        operLog.setRequestUri(request == null ? null : request.getRequestURI());
        operLog.setRequestMethod(request == null ? null : request.getMethod());
        operLog.setRequestParams(null);
        operLog.setOperator(user.getName());
        operLog.setIp(request == null ? null : getRemoteAddr(request));
        operLog.setIpAddr(null);
        operLog.setOperTime(new Date());
        operLog.setModule(module);
        operLog.setDesc(desc);
        operLog.setStatus(status);
        operLog.setErrMsg(errMsg);
        operLog.setCorpId(user.getCorpId());

        producerService.send("topic", "oper.log.save", JSON.toJSONBytes(operLog),
            String.valueOf(UUID.randomUUID()));
    }

    private String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        return StringUtils.isNotEmpty(ip) ? ip : request.getRemoteAddr();
    }

}
