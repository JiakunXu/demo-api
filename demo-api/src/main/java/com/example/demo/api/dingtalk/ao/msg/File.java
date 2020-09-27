package com.example.demo.api.dingtalk.ao.msg;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
public class File implements Serializable {

    private static final long serialVersionUID = -2414661995045144021L;

    @JSONField(name = "media_id")
    private String            mediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
