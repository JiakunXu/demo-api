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
public class MpTemplateMsg implements Serializable {

    private static final long serialVersionUID = 2865598852551062088L;

    /**
     * 公众号appid，要求与小程序有绑定且同主体.
     */
    private String            appid;

    /**
     * 公众号模板id.
     */
    @JSONField(name = "template_id")
    private String            templateId;

    /**
     * 公众号模板消息所要跳转的url.
     */
    private String            url;

    /**
     * 公众号模板消息所要跳转的小程序，小程序的必须与公众号具有绑定关系.
     */
    private MiniProgram       miniprogram;

    /**
     * 公众号模板消息的数据.
     */
    private Data              data;

}
