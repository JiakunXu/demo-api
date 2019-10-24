package com.example.demo.framework.config;

import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.exception.SystemException;
import com.example.demo.framework.response.ExceptionResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author JiakunXu
 */
@RestControllerAdvice
public class BaseExceptionHandler {

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
        ExceptionResponse response = new ExceptionResponse("-1",
            e == null ? "系统错误" : e.getMessage());

        return response;
    }

}
