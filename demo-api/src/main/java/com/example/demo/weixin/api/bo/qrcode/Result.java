package com.example.demo.weixin.api.bo.qrcode;

import com.alibaba.fastjson2.annotation.JSONField;
import com.example.demo.weixin.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 
 * @author JiakunXu
 * 
 */
@Getter
@Setter
public class Result extends BaseResult {

    @Serial
    private static final long serialVersionUID = 2091112238761729828L;

    private String            ticket;

    @JSONField(name = "expire_seconds")
    private Integer           expireSeconds;

    private String            url;

}
