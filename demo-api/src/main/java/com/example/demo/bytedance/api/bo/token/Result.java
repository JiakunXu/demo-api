package com.example.demo.bytedance.api.bo.token;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Getter;
import lombok.Setter;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Result implements Serializable {

    private static final long serialVersionUID = -7764030576212518819L;

    /**
     * 错误码.
     */
    @JSONField(name = "err_no")
    private Integer           errNo;

    /**
     * 错误信息.
     */
    @JSONField(name = "err_tips")
    private String            errTips;

    @JSONField(name = "data")
    private AccessToken       accessToken;

}
