package com.example.demo.weixin.api.bo.wxa;

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
public class Result extends BaseResult {

    @Serial
    private static final long serialVersionUID = -3112480442180365285L;

    @JSONField(name = "phone_info")
    private PhoneNumber       phoneNumber;

}
