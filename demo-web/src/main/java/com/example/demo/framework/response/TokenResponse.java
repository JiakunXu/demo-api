package com.example.demo.framework.response;

import com.example.demo.framework.constant.Constants;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class TokenResponse<T> extends AbstractResponse {

    private static final long serialVersionUID = 5661972515266196297L;

    private T                 token;

    private T                 ticket;

    public TokenResponse(T token) {
        this.setCode(Constants.SUCCESS);
        this.setToken(token);
    }

    public TokenResponse(Map<String, T> map) {
        this.setCode(Constants.SUCCESS);
        this.setToken(map.get("token"));
        this.setTicket(map.get("ticket"));
    }

}
