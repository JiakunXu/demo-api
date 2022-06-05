package com.example.demo.aliyun.api.bo.sms;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Data implements Serializable {

    private static final long serialVersionUID = -4701671447770358700L;

    /**
     * 发送回执ID，可根据该ID在接口QuerySendDetails中查询具体的发送状态。.
     */
    @JSONField(name = "BizId")
    private String            bizId;

    /**
     * 请求状态码。
     * 返回OK代表请求成功。
     * 其他错误码详见错误码列表。.
     */
    @JSONField(name = "Code")
    private String            code;

    /**
     * 状态码的描述。.
     */
    @JSONField(name = "Message")
    private String            message;

    /**
     * 请求ID。.
     */
    @JSONField(name = "RequestId")
    private String            requestId;

}
