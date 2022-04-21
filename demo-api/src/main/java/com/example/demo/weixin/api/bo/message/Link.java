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
public class Link implements Serializable {

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
