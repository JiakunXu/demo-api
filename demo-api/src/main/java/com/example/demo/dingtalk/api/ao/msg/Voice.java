package com.example.demo.dingtalk.api.ao.msg;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 语音消息.
 *
 * @author JiakunXu
 */
@Getter
@Setter
public class Voice implements Serializable {

    private static final long serialVersionUID = 4294199519111389389L;

    /**
     * 媒体文件id。2MB，播放长度不超过60s，AMR格式。
     * 可以通过媒体文件接口上传图片获取。.
     */
    @JSONField(name = "media_id")
    private String            mediaId;

    /**
     * 正整数，小于60，表示音频时长.
     */
    @JSONField(name = "duration")
    private String            duration;

}
