package com.example.demo.framework.aspectj;

import com.example.demo.framework.annotation.NotNull;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Aspect
@Component
public class NotNullAspect {

    @Before("within(com.example.demo.*.service.*)")
    public void doBefore(JoinPoint joinPoint) {
        Annotation[][] annotations = ((MethodSignature) joinPoint.getSignature()).getMethod()
            .getParameterAnnotations();

        for (int i = 0; i < annotations.length; i++) {
            for (Annotation annotation : annotations[i]) {
                if (annotation instanceof NotNull) {
                    if (joinPoint.getArgs()[i] == null) {
                        throw new RuntimeException("参数信息不能为空");
                    }
                }
            }
        }
    }

}
