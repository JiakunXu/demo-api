package com.example.demo.dingtalk.api.bo.msg;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 图片消息.
 *
 * @author JiakunXu
 */
@Getter
@Setter
public class Image implements Serializable {

    private static final long serialVersionUID = -9191587180805759665L;

    /**
     * 媒体文件id。可以通过媒体文件接口上传图片获取。建议宽600像素 x 400像素，宽高比3 : 2.
     */
    @JSONField(name = "media_id")
    private String            mediaId;

}
