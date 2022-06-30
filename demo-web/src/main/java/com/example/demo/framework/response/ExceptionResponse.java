package com.example.demo.framework.response;

/**
 * @author JiakunXu
 */
public class ExceptionResponse extends AbstractResponse {

    private static final long serialVersionUID = 5873823141210593582L;

    public ExceptionResponse(Integer code, String msg) {
        this.setCode(code);
        this.setMsg(msg);
    }

}
