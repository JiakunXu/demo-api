package com.example.demo.framework.response;

import com.example.demo.framework.constant.HttpStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.Map;

@Getter
@Setter
public class TokenResponse<T> extends AbstractResponse {

    @Serial
    private static final long serialVersionUID = 5661972515266196297L;

    private T                 token;

    private T                 ticket;

    public TokenResponse(T token) {
        this.setCode(HttpStatus.OK);
        this.setToken(token);
    }

    public TokenResponse(Map<String, T> map) {
        this.setCode(HttpStatus.OK);
        this.setToken(map.get("token"));
        this.setTicket(map.get("ticket"));
    }

}
