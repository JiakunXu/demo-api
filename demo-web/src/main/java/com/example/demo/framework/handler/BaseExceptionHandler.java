package com.example.demo.framework.handler;

import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.exception.SystemException;
import com.example.demo.framework.response.ExceptionResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

/**
 * @author JiakunXu
 */
@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = AccessDeniedException.class)
    public ExceptionResponse exceptionHandler(AccessDeniedException e) {
        ExceptionResponse response = new ExceptionResponse(Constants.FORBIDDEN, "没有权限，请联系管理员授权");

        return response;
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ExceptionResponse exceptionHandler(AuthenticationException e) {
        ExceptionResponse response = new ExceptionResponse(Constants.INTERNAL_SERVER_ERROR,
            e.getMessage());

        return response;
    }

    @ExceptionHandler(value = AsyncRequestTimeoutException.class)
    public ExceptionResponse exceptionHandler(AsyncRequestTimeoutException e) {
        ExceptionResponse response = new ExceptionResponse(Constants.REQUEST_TIMEOUT,
            e.getMessage());

        return response;
    }

    @ExceptionHandler(value = ServiceException.class)
    public ExceptionResponse exceptionHandler(ServiceException e) {
        ExceptionResponse response = new ExceptionResponse(e.getCode(), e.getMessage());

        return response;
    }

    @ExceptionHandler(value = SystemException.class)
    public ExceptionResponse exceptionHandler(SystemException e) {
        ExceptionResponse response = new ExceptionResponse(e.getCode(), e.getMessage());

        return response;
    }

    @ExceptionHandler(value = Exception.class)
    public ExceptionResponse exceptionHandler(Exception e) {
        ExceptionResponse response = new ExceptionResponse(Constants.SERVICE_UNAVAILABLE,
            e == null ? "系统错误"
                : (e.getCause() == null ? e.getMessage() : e.getCause().getMessage()));

        return response;
    }

}
