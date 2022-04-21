package com.example.demo.weixin.api.bo.message;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author JiakunXu
 * 
 */
@Getter
@Setter
@ToString
public class Template implements Serializable {

    private static final long serialVersionUID = -7355661588948787559L;

    /**
     * 接收者openid.
     */
    @JSONField(name = "touser")
    private String            toUser;

    /**
     * 模板ID.
     */
    @JSONField(name = "template_id")
    private String            templateId;

    /**
     * 模板跳转链接（海外帐号没有跳转能力）.
     */
    private String            url;

    /**
     * 跳小程序所需数据，不需跳小程序可不用传该数据.
     */
    @JSONField(name = "miniprogram")
    private MiniProgram       miniProgram;

    /**
     * 模板数据.
     */
    private Data              data;

}
