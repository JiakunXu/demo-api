package com.example.demo.framework.response;

import com.example.demo.framework.constant.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse<T> extends AbstractResponse {

    private static final long serialVersionUID = 5661972515266196297L;

    private T                 token;

    public TokenResponse(T object) {
        this.setCode(Constants.SUCCESS);
        this.setToken(object);
    }

}
