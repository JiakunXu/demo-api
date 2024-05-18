package com.example.demo.framework.aspectj;

import com.example.demo.framework.annotation.NotBlank;
import com.example.demo.framework.annotation.NotEmpty;
import com.example.demo.framework.annotation.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.List;

@Aspect
@Component
public class ValidateAspect {

    @Before("within(com.example.demo.*.service.*)")
    public void doBefore(JoinPoint joinPoint) {
        Annotation[][] annotations = ((MethodSignature) joinPoint.getSignature()).getMethod()
            .getParameterAnnotations();

        for (int i = 0; i < annotations.length; i++) {
            for (Annotation annotation : annotations[i]) {
                Object arg = joinPoint.getArgs()[i];
                if (annotation instanceof NotBlank) {
                    if (arg == null) {
                        thorw(((NotBlank) annotation).value());
                    } else if (arg instanceof String && StringUtils.isBlank((String) arg)) {
                        thorw(((NotBlank) annotation).value());
                    }
                } else if (annotation instanceof NotEmpty) {
                    if (arg == null) {
                        thorw(((NotEmpty) annotation).value());
                    } else if (arg instanceof String && StringUtils.isEmpty((String) arg)) {
                        thorw(((NotEmpty) annotation).value());
                    }
                } else if (annotation instanceof NotNull) {
                    if (arg == null) {
                        thorw(((NotNull) annotation).value());
                    } else if (arg instanceof List && ((List<?>) arg).isEmpty()) {
                        thorw(((NotNull) annotation).value());
                    } else if (arg.getClass().isArray() && Array.getLength(arg) == 0) {
                        thorw(((NotNull) annotation).value());
                    }
                }
            }
        }
    }

    private void thorw(String message) {
        throw new RuntimeException(StringUtils.isBlank(message) ? "参数信息不能为空" : message);
    }

}
