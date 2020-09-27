package com.example.demo.api.dingtalk.ao.msg;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * OA消息.
 *
 * @author JiakunXu
 */
public class Oa implements Serializable {

    private static final long serialVersionUID = -5096367706138112295L;

    @JSONField(name = "message_url")
    private String            messageUrl;

    @JSONField(name = "pc_message_url")
    private String            pcMessageUrl;

    /**
     * 消息头部内容.
     */
    private Head              head;

    /**
     * 消息体.
     */
    private Body              body;

    public String getMessageUrl() {
        return messageUrl;
    }

    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }

    public String getPcMessageUrl() {
        return pcMessageUrl;
    }

    public void setPcMessageUrl(String pcMessageUrl) {
        this.pcMessageUrl = pcMessageUrl;
    }

    public Head getHead() {
        return head;
    }

    public void setHead(Head head) {
        this.head = head;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
