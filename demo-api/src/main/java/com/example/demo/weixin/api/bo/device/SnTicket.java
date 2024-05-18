package com.example.demo.weixin.api.bo.device;

import com.alibaba.fastjson2.annotation.JSONField;
import com.example.demo.weixin.api.bo.BaseResult;

import java.io.Serial;

/**
 * @author JiakunXu
 */
public class SnTicket extends BaseResult {

    @Serial
    private static final long serialVersionUID = -5722203466236937492L;

    /**
     * 设备票据，5分钟内有效.
     */
    @JSONField(name = "sn_ticket")
    private String            snTicket;

}
