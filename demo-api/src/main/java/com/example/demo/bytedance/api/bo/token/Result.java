package com.example.demo.bytedance.api.bo.token;

import com.alibaba.fastjson.annotation.JSONField;

import com.example.demo.bytedance.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Result extends BaseResult {

    private static final long serialVersionUID = -7764030576212518819L;

    @JSONField(name = "data")
    private AccessToken       accessToken;

}
