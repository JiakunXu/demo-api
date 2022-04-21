package com.example.demo.weixin.api.bo.qrcode;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.demo.weixin.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author JiakunXu
 * 
 */
@Getter
@Setter
public class Result extends BaseResult {

    private static final long serialVersionUID = 2091112238761729828L;

    private String            ticket;

    @JSONField(name = "expire_seconds")
    private Integer           expireSeconds;

    private String            url;

}
