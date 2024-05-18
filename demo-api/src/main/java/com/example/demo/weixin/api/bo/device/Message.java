package com.example.demo.weixin.api.bo.device;

import com.alibaba.fastjson2.annotation.JSONField;
import com.example.demo.weixin.api.bo.message.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
@ToString
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = -7995161136457173223L;

    /**
     * 接收者（用户）的 openid 列表.
     */
    @JSONField(name = "to_openid_list")
    private String[]          toOpenidList;

    /**
     * 所需下发的订阅模板id.
     */
    @JSONField(name = "template_id")
    private String            templateId;

    /**
     * 设备唯一序列号。由厂商分配，长度不能超过128字节。字符只接受数字，大小写字母，下划线（_）和连字符（-）.
     */
    private String            sn;

    /**
     * 设备型号 id ，通过注册设备获得.
     */
    @JSONField(name = "model_id")
    private String            modelId;

    /**
     * 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转.
     */
    private String            page;

    /**
     * 模板内容，格式形如 { "key1": { "value": "xxx" }, "key2": { "value": "xxx" } } ，value 为枚举值.
     */
    private Data              data;

    /**
     * 跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版.
     */
    @JSONField(name = "miniprogram_state")
    private String            miniprogramState;

    /**
     * 进入小程序查看”的语言类型，支持zh_CN(简体中文)、en_US(英文)、zh_HK(繁体中文)、zh_TW(繁体中文)，默认为zh_CN.
     */
    private String            lang;

}
