package com.example.demo.api.bytedance.ao;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
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
