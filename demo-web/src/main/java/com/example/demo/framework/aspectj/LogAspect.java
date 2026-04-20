package com.example.demo.framework.aspectj;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.annotation.Log;
import com.example.demo.operate.api.bo.OperateLog;
import com.example.demo.mq.api.ProducerService;
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
            OperateLog.Status.SUCCESS.value, null);
    }

    @AfterThrowing(value = "@annotation(log)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log log, Exception e) {
        handle(joinPoint.getTarget().getClass().getSimpleName(), log.module(), log.desc(),
            OperateLog.Status.FAIL.value, e.getMessage());
    }

    private void handle(String clazz, String module, String desc, String status, String errMsg) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = "anonymousUser".equals(authentication.getName()) ? new User("anonymous")
            : (LoginUser) authentication.getPrincipal();

        OperateLog operateLog = new OperateLog();
        operateLog.setClazz(clazz);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes();
        HttpServletRequest request = attributes == null ? null : attributes.getRequest();

        operateLog.setRequestUri(request == null ? null : request.getRequestURI());
        operateLog.setRequestMethod(request == null ? null : request.getMethod());
        operateLog.setRequestParams(null);
        operateLog.setOperator(
            user.getId() == null ? user.getName() : (user.getName() + "@" + user.getId()));
        operateLog.setIp(request == null ? null : getRemoteAddr(request));
        operateLog.setIpAddr(null);
        operateLog.setOperTime(new Date());
        operateLog.setModule(module);
        operateLog.setDesc(desc);
        operateLog.setStatus(status);
        operateLog.setErrMsg(errMsg);
        operateLog.setCorpId(user.getCorpId());

        producerService.send("topic", "oper.log.save", JSON.toJSONBytes(operateLog),
            String.valueOf(UUID.randomUUID()));
    }

    private String getRemoteAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");

        return StringUtils.isNotEmpty(ip) ? ip : request.getRemoteAddr();
    }

}
