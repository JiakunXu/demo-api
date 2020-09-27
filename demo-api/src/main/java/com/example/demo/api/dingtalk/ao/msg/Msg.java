package com.example.demo.api.dingtalk.ao.msg;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
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

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Voice getVoice() {
        return voice;
    }

    public void setVoice(Voice voice) {
        this.voice = voice;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public Oa getOa() {
        return oa;
    }

    public void setOa(Oa oa) {
        this.oa = oa;
    }

    public Markdown getMarkdown() {
        return markdown;
    }

    public void setMarkdown(Markdown markdown) {
        this.markdown = markdown;
    }

    public ActionCard getActionCard() {
        return actionCard;
    }

    public void setActionCard(ActionCard actionCard) {
        this.actionCard = actionCard;
    }
}
