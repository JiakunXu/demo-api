package com.example.demo.weixin.api.bo.menu;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author JiakunXu
 *
 */
@Getter
@Setter
@ToString
public class Menu implements Serializable {

    @Serial
    private static final long serialVersionUID = -3786757350633940882L;

    @JSONField(name = "button")
    private List<Button>      buttonList;

    @Getter
    @Setter
    public static class Button implements Serializable {

        @Serial
        private static final long serialVersionUID = 8705272541703114748L;

        /**
         * 菜单的响应动作类型，view表示网页类型，click表示点击类型，miniprogram表示小程序类型.
         */
        private String            type;

        /**
         * 菜单标题，不超过16个字节，子菜单不超过60个字节.
         */
        private String            name;

        /**
         * 菜单KEY值，用于消息接口推送，不超过128字节.
         */
        private String            key;

        /**
         * 网页 链接，用户点击菜单可打开链接，不超过1024字节。 type为miniprogram时，不支持小程序的老版本客户端将打开本url.
         */
        private String            url;

        /**
         * 调用新增永久素材接口返回的合法media_id.
         */
        @JSONField(name = "media_id")
        private String            mediaId;

        /**
         * 小程序的appid（仅认证公众号可配置）.
         */
        private String            appid;

        /**
         * 小程序的页面路径.
         */
        @JSONField(name = "pagepath")
        private String            pagePath;

        /**
         * 发布后获得的合法 article_id.
         */
        @JSONField(name = "article_id")
        private String            articleId;

        @JSONField(name = "sub_button")
        private List<Button>      buttonList;

    }

}
