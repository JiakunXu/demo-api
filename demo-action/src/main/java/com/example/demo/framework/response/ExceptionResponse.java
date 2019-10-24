package com.example.demo.framework.response;

/**
 * @author JiakunXu
 */
public class ExceptionResponse extends AbstractResponse {

    public ExceptionResponse(String code, String msg) {
        this.setCode(code);
        this.setMsg(msg);
    }

}
