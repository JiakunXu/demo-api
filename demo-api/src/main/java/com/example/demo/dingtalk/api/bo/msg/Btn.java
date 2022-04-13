package com.example.demo.dingtalk.api.bo.msg;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Btn implements Serializable {

    private static final long serialVersionUID = -8236177777900407174L;

    /**
     * 使用独立跳转ActionCard样式时的按钮的标题，最长20个字符.
     */
    private String            title;

    /**
     * 消息点击链接地址，当发送消息为小程序时支持小程序跳转链接，最长500个字符.
     */
    @JSONField(name = "action_url")
    private String            actionUrl;

}
