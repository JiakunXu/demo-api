package com.example.demo.framework.aspectj;

import com.example.demo.aliyun.api.ProducerService;
import com.example.demo.framework.annotation.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    @Autowired
    private ProducerService producerService;

    @AfterReturning(pointcut = "@annotation(log)", returning = "object")
    public void doAfterReturning(JoinPoint joinPoint, Log log, Object object) {

    }

    @AfterThrowing(value = "@annotation(log)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log log, Exception e) {

    }

}
