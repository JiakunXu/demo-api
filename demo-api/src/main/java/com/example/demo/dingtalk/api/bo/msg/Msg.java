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
public class Msg implements Serializable {

    private static final long serialVersionUID = -8274817384455951791L;

    /**
     * 消息类型.
     */
    @JSONField(name = "msgtype")
    private String            msgType;

    /**
     * 文本消息.
     */
    private Text              text;

    /**
     * 图片消息.
     */
    private Image             image;

    /**
     * 语音消息.
     */
    private Voice             voice;

    /**
     * 文件消息.
     */
    private File              file;

    /**
     * 链接消息.
     */
    private Link              link;

    /**
     * OA消息.
     */
    private Oa                oa;

    /**
     * markdown消息.
     */
    private Markdown          markdown;

    /**
     * 卡片消息.
     */
    @JSONField(name = "action_card")
    private ActionCard        actionCard;

}
