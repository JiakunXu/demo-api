package com.example.demo.weixin.api.bo.js;

import com.alibaba.fastjson2.annotation.JSONField;
import com.example.demo.weixin.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class JsapiTicket extends BaseResult {

    @Serial
    private static final long serialVersionUID = -5666192769421521155L;

    /**
     * 获取到的凭证.
     */
    private String            ticket;

    /**
     * 凭证的有效时间（秒）.
     */
    @JSONField(name = "expires_in")
    private Integer           expiresIn;

}
