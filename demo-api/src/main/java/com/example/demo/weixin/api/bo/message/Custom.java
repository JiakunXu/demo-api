package com.example.demo.weixin.api.bo.message;

import java.io.Serializable;
import java.util.List;

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
    public static class Text implements Serializable {

        private static final long serialVersionUID = 552698313157925708L;

        /**
         * 消息内容.
         */
        private String            content;

    }

    @Getter
    @Setter
    public static class Image implements Serializable {

        private static final long serialVersionUID = -8440592062810395126L;

        /**
         * 图片媒体文件id，可以调用上传临时素材或者永久素材接口获取,永久素材media_id必须由发消息的应用创建.
         */
        @JSONField(name = "media_id")
        private String            mediaId;

    }

    @Getter
    @Setter
    public static class Voice implements Serializable {

        private static final long serialVersionUID = -8380547800046434617L;

        /**
         * 语音文件id，可以调用上传临时素材或者永久素材接口获取.
         */
        @JSONField(name = "media_id")
        private String            mediaId;

    }

    @Getter
    @Setter
    public static class Video implements Serializable {

        private static final long serialVersionUID = -6001711073891320714L;

        /**
         * 视频媒体文件id，可以调用上传临时素材或者永久素材接口获取.
         */
        @JSONField(name = "media_id")
        private String            mediaId;

        /**
         * 缩略图.
         */
        @JSONField(name = "thumb_media_id")
        private String            thumbMediaId;

        /**
         * 视频消息的标题.
         */
        private String            title;

        /**
         * 视频消息的描述.
         */
        private String            description;

    }

    @Getter
    @Setter
    public static class Music implements Serializable {

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

    @Getter
    @Setter
    public static class News implements Serializable {

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
