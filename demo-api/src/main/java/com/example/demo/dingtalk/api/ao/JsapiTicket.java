package com.example.demo.dingtalk.api.ao;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author JiakunXu
 */
@Getter
@Setter
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

}
