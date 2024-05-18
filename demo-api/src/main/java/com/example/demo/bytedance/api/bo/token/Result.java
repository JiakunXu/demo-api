package com.example.demo.bytedance.api.bo.token;

import com.alibaba.fastjson2.annotation.JSONField;

import com.example.demo.bytedance.api.bo.BaseResult;
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
    private static final long serialVersionUID = -7764030576212518819L;

    @JSONField(name = "data")
    private AccessToken       accessToken;

}
