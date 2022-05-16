package com.example.demo.weixin.api.bo.material;

import com.alibaba.fastjson2.annotation.JSONField;
import com.example.demo.weixin.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author JiakunXu
 *
 */
@Getter
@Setter
public class Count extends BaseResult {

    private static final long serialVersionUID = 1615105609635167004L;

    /**
     * 语音总数量.
     */
    @JSONField(name = "voice_count")
    private int               voiceCount;

    /**
     * 视频总数量.
     */
    @JSONField(name = "video_count")
    private int               videoCount;

    /**
     * 图片总数量.
     */
    @JSONField(name = "image_count")
    private int               imageCount;

    /**
     * 图文总数量.
     */
    @JSONField(name = "news_count")
    private int               newsCount;

}
