package com.example.demo.bytedance.api.bo.message;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Message implements Serializable {

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

}
