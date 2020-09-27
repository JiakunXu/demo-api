package com.example.demo.api.dingtalk.ao;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author JiakunXu
 */
public class JsapiTicket extends BaseResult {

    private static final long serialVersionUID = 833025693307240187L;

    /**
     * 用于JSAPI的临时票据.
     */
    private String            ticket;

    /**
     * 票据过期时间.
     */
    @JSONField(name = "expires_in")
    private Long              expiresIn;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
