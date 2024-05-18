package com.example.demo.weixin.api.bo.message;

import com.alibaba.fastjson2.annotation.JSONField;
import com.example.demo.weixin.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.math.BigInteger;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Result extends BaseResult {

    @Serial
    private static final long serialVersionUID = -8725706372633271928L;

    @JSONField(name = "msgid")
    private BigInteger        msgId;

}
