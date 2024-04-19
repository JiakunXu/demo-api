package com.example.demo.weixin.api.bo.message;

import java.io.Serializable;
import java.util.List;

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
public class News implements Serializable {

    private static final long serialVersionUID = -3435707328473697197L;

    @JSONField(name = "articles")
    private List<Article>     articleList;

    @Getter
    @Setter
    public static class Article implements Serializable {

        private static final long serialVersionUID = -4200095293164497228L;

        /**
         * 标题.
         */
        private String            title;

        /**
         * 描述.
         */
        private String            description;

        /**
         * 点击后跳转的链接.
         */
        private String            url;

        /**
         * 图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80。如不填，在客户端不显示图片.
         */
        @JSONField(name = "picurl")
        private String            picUrl;

    }

}
