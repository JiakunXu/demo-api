package com.example.demo.api.weixin.ao.sns;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author JiakunXu
 */
public class Watermark implements Serializable {

    private static final long serialVersionUID = -946609595011819810L;

    /**
     * 敏感数据归属 appId，开发者可校验此参数与自身 appId 是否一致.
     */
    @JSONField(name = "appid")
    private String            appId;

    /**
     * 敏感数据获取的时间戳, 开发者可以用于数据时效性校验.
     */
    @JSONField(name = "timestamp")
    private int               timestamp;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
