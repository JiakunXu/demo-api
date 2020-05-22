package com.example.demo.api.bytedance.bo;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.demo.framework.bo.BaseBo;

import java.math.BigInteger;

/**
 * @author JiakunXu
 */
public class Message extends BaseBo {

    private static final long serialVersionUID = -5501483433983115912L;

    private BigInteger        id;

    /**
     * 小程序的 ID.
     */
    @JSONField(name = "ToUserName")
    private String            toUserName;

    /**
     * 发送消息用户的 openid.
     */
    @JSONField(name = "FromUserName")
    private String            fromUserName;

    /**
     * 消息创建时间.
     */
    @JSONField(name = "CreateTime")
    private BigInteger        createTime;

    /**
     * 消息类型.
     */
    @JSONField(name = "MsgType")
    private String            msgType;

    /**
     * 消息类型，文本消息为 text，图片消息为 image.
     */
    @JSONField(name = "Content")
    private String            content;

    /**
     * 图片链接.
     */
    @JSONField(name = "PicUrl")
    private String            picUrl;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public BigInteger getCreateTime() {
        return createTime;
    }

    public void setCreateTime(BigInteger createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

}
