package com.example.demo.framework.response;

import com.example.demo.framework.constant.Constants;

/**
 * @author JiakunXu
 */
public class ObjectResponse<T> extends AbstractResponse {

    private T object;

    public ObjectResponse(T object) {
        this.setCode(Constants.SUCCESS);
        this.setObject(object);
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
