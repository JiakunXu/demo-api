package com.example.demo.api.dingtalk.ao.msg;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author JiakunXu
 */
public class Msg {

    /**
     * 消息类型.
     */
    @JSONField(name = "msgtype")
    private String msgType;

    private Text   text;

    private Image  image;

    private Voice  voice;

    private File   file;

    private Link   link;

}
