package com.example.demo.weixin.api.bo.wxa;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.demo.weixin.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Result extends BaseResult {

    private static final long serialVersionUID = -3112480442180365285L;

    @JSONField(name = "phone_info")
    private PhoneNumber       phoneNumber;

}
