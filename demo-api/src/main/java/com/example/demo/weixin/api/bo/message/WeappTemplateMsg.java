package com.example.demo.weixin.api.bo.message;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class WeappTemplateMsg implements Serializable {

    private static final long serialVersionUID = -8184334201640264914L;

    /**
     * 小程序模板ID.
     */
    @JSONField(name = "template_id")
    private String            templateId;

    /**
     * 小程序页面路径.
     */
    private String            page;

    /**
     * 小程序模板消息formid.
     */
    @JSONField(name = "form_id")
    private String            formId;

    /**
     * 小程序模板数据.
     */
    private Data              data;

    /**
     * 小程序模板放大关键词.
     */
    @JSONField(name = "emphasis_keyword")
    private String            emphasisKeyword;

}
