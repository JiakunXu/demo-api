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
public class MiniProgramPage implements Serializable {

    private static final long serialVersionUID = -1103517896641557915L;

    private String            title;

    /**
     * 小程序 无此参数.
     */
    private String            appid;

    @JSONField(name = "pagepath")
    private String            pagePath;

    @JSONField(name = "thumb_media_id")
    private String            thumbMediaId;

}
