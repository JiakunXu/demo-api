package com.example.demo.weixin.api.bo.message;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author JiakunXu
 * 
 */
@Getter
@Setter
public class Image implements Serializable {

    private static final long serialVersionUID = -8440592062810395126L;

    /**
     * 图片媒体文件id，可以调用上传临时素材或者永久素材接口获取,永久素材media_id必须由发消息的应用创建.
     */
    @JSONField(name = "media_id")
    private String            mediaId;

}
