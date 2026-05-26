package com.example.demo.framework.response;

import com.example.demo.framework.constant.HttpStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class ObjectResponse<T> extends AbstractResponse {

    @Serial
    private static final long serialVersionUID = 5661972515266196297L;

    private T                 data;

    public ObjectResponse(T data) {
        this.setCode(HttpStatus.OK);
        this.setData(data);
    }

}
