package com.example.demo.framework.exception;

/**
 * RuntimeException.
 * 
 * @author xujiakun
 * 
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 5259805918456538208L;

    private final Integer     code;

    public ServiceException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ServiceException(Integer code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
