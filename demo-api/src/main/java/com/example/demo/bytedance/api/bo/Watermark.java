package com.example.demo.bytedance.api.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Watermark implements Serializable {

    private static final long serialVersionUID = -4069122113583910820L;

    /**
     * 数据源小程序 id.
     */
    @JSONField(name = "appid")
    private String            appId;

    /**
     * 时间戳，可以用于检查数据的时效性.
     */
    @JSONField(name = "timestamp")
    private int               timestamp;

}
