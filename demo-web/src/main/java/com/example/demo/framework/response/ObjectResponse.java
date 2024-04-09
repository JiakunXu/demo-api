package com.example.demo.framework.response;

import com.example.demo.framework.constant.Constants;
import lombok.Getter;
import lombok.Setter;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class ObjectResponse<T> extends AbstractResponse {

    private static final long serialVersionUID = 5661972515266196297L;

    private T                 data;

    public ObjectResponse(T data) {
        this.setCode(Constants.SUCCESS);
        this.setData(data);
    }

}
