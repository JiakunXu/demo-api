package com.example.demo.framework.handler;

import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.exception.SystemException;
import com.example.demo.framework.response.ExceptionResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author JiakunXu
 */
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = AccessDeniedException.class)
    public ExceptionResponse exceptionHandler(AccessDeniedException e) {
        ExceptionResponse response = new ExceptionResponse(Constants.INSUFFICIENT_PERMISSIONS,
            "没有权限，请联系管理员授权");

        return response;
    }

    @ExceptionHandler(value = ServiceException.class)
    public ExceptionResponse exceptionHandler(ServiceException e) {
        ExceptionResponse response = new ExceptionResponse(e.getErrorCode(), e.getMessage());

        return response;
    }

    @ExceptionHandler(value = SystemException.class)
    public ExceptionResponse exceptionHandler(SystemException e) {
        ExceptionResponse response = new ExceptionResponse(e.getErrorCode(), e.getMessage());

        return response;
    }

    @ExceptionHandler(value = Exception.class)
    public ExceptionResponse exceptionHandler(Exception e) {
        ExceptionResponse response = new ExceptionResponse(Constants.UNKNOW_ERROR,
            e == null ? "系统错误" : e.toString());

        return response;
    }

}
