package com.example.demo.dingtalk.api.ao.msg;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 消息体.
 * 
 * @author JiakunXu
 */
@Getter
@Setter
public class Body implements Serializable {

    private static final long serialVersionUID = -593192103771521711L;

    /**
     * 消息体的标题，建议50个字符以内.
     */
    private String            title;

    /**
     * 消息体的表单，最多显示6个，超过会被隐藏.
     */
    private Form              form;

    /**
     * 单行富文本信息.
     */
    private Rich              rich;

    /**
     * 消息体的内容，最多显示3行.
     */
    private String            content;

    /**
     * 消息体中的图片，支持图片资源@mediaId.
     */
    private String            image;

    /**
     * 自定义的附件数目。此数字仅供显示，钉钉不作验证.
     */
    @JSONField(name = "file_count")
    private String            fileCount;

    /**
     * 自定义的作者名字.
     */
    private String            author;

}
