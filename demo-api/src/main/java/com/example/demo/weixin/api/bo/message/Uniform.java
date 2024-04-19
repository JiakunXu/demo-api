package com.example.demo.weixin.api.bo.message;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
@ToString
public class Uniform implements Serializable {

    private static final long serialVersionUID = -1761801486337321558L;

    @JSONField(name = "touser")
    private String            toUser;

    @JSONField(name = "weapp_template_msg")
    private WeappTemplateMsg  weappTemplateMsg;

    @JSONField(name = "mp_template_msg")
    private MpTemplateMsg     mpTemplateMsg;

    @Getter
    @Setter
    public static class WeappTemplateMsg implements Serializable {

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

    @Getter
    @Setter
    public static class MpTemplateMsg implements Serializable {

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

}
