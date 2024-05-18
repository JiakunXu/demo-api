package com.example.demo.weixin.api.bo.message;

import java.io.Serializable;

import com.alibaba.fastjson2.annotation.JSONField;
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

    @Getter
    @Setter
    public static class MiniProgram implements Serializable {

        private static final long serialVersionUID = 4742658373201267387L;

        /**
         * 所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系，暂不支持小游戏）.
         */
        private String            appid;

        /**
         * 所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar），要求该小程序已发布，暂不支持小游戏.
         */
        @JSONField(name = "pagepath")
        private String            pagePath;

    }

}
