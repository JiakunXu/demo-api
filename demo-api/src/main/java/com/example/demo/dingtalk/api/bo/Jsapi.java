package com.example.demo.dingtalk.api.bo;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Jsapi implements Serializable {

    private static final long serialVersionUID = -6957345637848956947L;

    /**
     * 微应用ID.
     */
    private String            agentId;

    /**
     * 企业ID.
     */
    private String            corpId;

    /**
     * 生成签名的时间戳.
     */
    @JSONField(name = "timeStamp")
    private String            timestamp;

    /**
     * 生成签名的随机串.
     */
    private String            nonceStr;

    /**
     * JSAPI签名.
     */
    private String            signature;

}
