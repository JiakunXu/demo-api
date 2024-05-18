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
public class Custom implements Serializable {

    private static final long serialVersionUID = 733672350056046345L;

    @JSONField(name = "touser")
    private String            toUser;

    @JSONField(name = "msgtype")
    private String            msgType;

    private Text              text;

    private Image             image;

    private Voice             voice;

    private Video             video;

    private Music             music;

    private News              news;

    @JSONField(name = "mpnews")
    private MpNews            mpNews;

    @JSONField(name = "mpnewsarticle")
    private MpNewsArticle     mpNewsArticle;

    @JSONField(name = "wxcard")
    private WxCard            wxCard;

    @JSONField(name = "miniprogrampage")
    private MiniProgramPage   miniProgramPage;

    private Link              link;

    @Getter
    @Setter
    public static class MpNews implements Serializable {

        private static final long serialVersionUID = 7061489935805032955L;

        @JSONField(name = "media_id")
        private String            mediaId;

    }

    @Getter
    @Setter
    public static class MpNewsArticle implements Serializable {

        private static final long serialVersionUID = 1293536183206475031L;

        @JSONField(name = "article_id")
        private String            articleId;

    }

    @Getter
    @Setter
    public static class WxCard implements Serializable {

        private static final long serialVersionUID = 5035057128920912659L;

        @JSONField(name = "card_id")
        private String            cardId;

    }

    @Getter
    @Setter
    public static class MiniProgramPage implements Serializable {

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

    @Getter
    @Setter
    public static class Link implements Serializable {

        private static final long serialVersionUID = 8488659987271370349L;

        /**
         * 消息标题.
         */
        private String            title;

        /**
         * 图文链接消息.
         */
        private String            description;

        /**
         * 图文链接消息被点击后跳转的链接.
         */
        private String            url;

        /**
         * 图文链接消息的图片链接，支持 JPG、PNG 格式，较好的效果为大图 640 X 320，小图 80 X 80.
         */
        @JSONField(name = "thumb_url")
        private String            thumbUrl;

    }

}
