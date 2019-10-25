package com.example.demo.api.weixin.ao;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author JiakunXu
 */
public class JsapiTicket extends BaseResult {

    private static final long serialVersionUID = -5666192769421521155L;

    /**
     * 获取到的凭证.
     */
    private String            ticket;

    /**
     * 凭证的有效时间（秒）.
     */
    @JSONField(name = "expires_in")
    private int               expiresIn;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

}
