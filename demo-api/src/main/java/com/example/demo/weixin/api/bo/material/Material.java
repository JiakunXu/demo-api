package com.example.demo.weixin.api.bo.material;

import com.alibaba.fastjson2.annotation.JSONField;
import com.example.demo.weixin.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;

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

    }

}
