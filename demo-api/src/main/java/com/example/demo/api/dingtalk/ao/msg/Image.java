package com.example.demo.api.dingtalk.ao.msg;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
public class Image implements Serializable {

    private static final long serialVersionUID = -9191587180805759665L;

    @JSONField(name = "media_id")
    private String            mediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
