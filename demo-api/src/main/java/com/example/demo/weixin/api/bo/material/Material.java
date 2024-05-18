package com.example.demo.weixin.api.bo.material;

import com.alibaba.fastjson2.annotation.JSONField;
import com.example.demo.weixin.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;

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
public class Material extends BaseResult {

    @Serial
    private static final long serialVersionUID = -2800232607622280767L;

    /**
     * 该类型的素材的总数.
     */
    @JSONField(name = "total_count")
    private int               totalCount;

    /**
     * 本次调用获取的素材的数量.
     */
    @JSONField(name = "item_count")
    private int               itemCount;

    @JSONField(name = "item")
    private List<Item>        itemList;

    @Getter
    @Setter
    public static class Item implements Serializable {

        @Serial
        private static final long serialVersionUID = -1234436809778010556L;

        @JSONField(name = "media_id")
        private String            mediaId;

        /**
         * 文件名称.
         */
        private String            name;

        private Content           content;

        /**
         * 图文页的URL，或者，当获取的列表是图片素材列表时，该字段是图片的URL.
         */
        private String            url;

        /**
         * 这篇图文消息素材的最后更新时间.
         */
        @JSONField(name = "update_time")
        private String            updateTime;

        @Getter
        @Setter
        public static class Content implements Serializable {

            @Serial
            private static final long serialVersionUID = 8487304781166093559L;

            @JSONField(name = "news_item")
            private List<NewsItem>    newsItemList;

            @Getter
            @Setter
            public static class NewsItem implements Serializable {

                @Serial
                private static final long serialVersionUID = 2061428629185051714L;

                /**
                 * 图文消息的标题.
                 */
                private String            title;

                /**
                 * 图文消息的封面图片素材id（必须是永久mediaID）.
                 */
                @JSONField(name = "thumb_media_id")
                private String            thumbMediaId;

                /**
                 * 是否显示封面，0为false，即不显示，1为true，即显示.
                 */
                @JSONField(name = "show_cover_pic")
                private Integer           showCoverPic;

                /**
                 * 作者.
                 */
                private String            author;

                /**
                 * 图文消息的摘要，仅有单图文消息才有摘要，多图文此处为空.
                 */
                private String            digest;

                /**
                 * 图文消息的具体内容，支持HTML标签，必须少于2万字符，小于1M，且此处会去除JS.
                 */
                private String            content;

                /**
                 * 图文页的URL，或者，当获取的列表是图片素材列表时，该字段是图片的URL.
                 */
                private String            url;

                /**
                 * 图文消息的原文地址，即点击“阅读原文”后的URL.
                 */
                @JSONField(name = "content_source_url")
                private String            contentSourceUrl;

            }

        }

    }

}
