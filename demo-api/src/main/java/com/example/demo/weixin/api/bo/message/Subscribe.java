package com.example.demo.weixin.api.bo.message;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 * @author JiakunXu
 *
 */
@Getter
@Setter
@ToString
public class Subscribe implements Serializable {

    private static final long serialVersionUID = 6350388774012300840L;

    /**
     * 接收者（用户）的 openid.
     */
    @JSONField(name = "touser")
    private String            toUser;

    /**
     * 所需下发的订阅模板id.
     */
    @JSONField(name = "template_id")
    private String            templateId;

    /**
     * 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转.
     */
    private String            page;

    /**
     * 模板内容，格式形如 { "key1": { "value": any }, "key2": { "value": any } }.
     */
    private Data              data;

    /**
     * 跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版.
     */
    @JSONField(name = "miniprogram_state")
    private String            miniProgramState;

    /**
     * 进入小程序查看”的语言类型，支持zh_CN(简体中文)、en_US(英文)、zh_HK(繁体中文)、zh_TW(繁体中文)，默认为zh_CN.
     */
    private String            lang;

}
