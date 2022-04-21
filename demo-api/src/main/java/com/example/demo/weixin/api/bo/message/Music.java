package com.example.demo.weixin.api.bo.message;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 * @author JiakunXu
 *
 */
@Getter
@Setter
public class Music implements Serializable {

    private static final long serialVersionUID = 4770849345906757609L;

    /**
     * 音乐消息的标题.
     */
    private String            title;

    /**
     * 音乐消息的描述.
     */
    private String            description;

    /**
     * 音乐链接.
     */
    @JSONField(name = "musicurl")
    private String            musicUrl;

    /**
     * 高品质音乐链接，wifi环境优先使用该链接播放音乐.
     */
    @JSONField(name = "hqmusicurl")
    private String            hqMusicUrl;

    /**
     * 缩略图.
     */
    @JSONField(name = "thumb_media_id")
    private String            thumbMediaId;

}
