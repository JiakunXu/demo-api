package com.example.demo.framework.exception;

import java.io.Serial;

/**
 * Exception.
 * 
 * @author xujiakun
 * 
 */
public class SystemException extends Exception {

    @Serial
    private static final long serialVersionUID = 5259805918456538208L;

    private final Integer     code;

    public SystemException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public SystemException(Integer code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public SystemException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public SystemException(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
