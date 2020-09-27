package com.example.demo.api.dingtalk.ao.msg;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 图片消息.
 *
 * @author JiakunXu
 */
public class Image implements Serializable {

    private static final long serialVersionUID = -9191587180805759665L;

    /**
     * 媒体文件id。可以通过媒体文件接口上传图片获取。建议宽600像素 x 400像素，宽高比3 : 2.
     */
    @JSONField(name = "media_id")
    private String            mediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
