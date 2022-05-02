package com.example.demo.weixin.api.bo.message;

import java.io.Serializable;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author JiakunXu
 * 
 */
@Getter
@Setter
public class Video implements Serializable {

    private static final long serialVersionUID = -6001711073891320714L;

    /**
     * 视频媒体文件id，可以调用上传临时素材或者永久素材接口获取.
     */
    @JSONField(name = "media_id")
    private String            mediaId;

    /**
     * 缩略图.
     */
    @JSONField(name = "thumb_media_id")
    private String            thumbMediaId;

    /**
     * 视频消息的标题.
     */
    private String            title;

    /**
     * 视频消息的描述.
     */
    private String            description;

}
