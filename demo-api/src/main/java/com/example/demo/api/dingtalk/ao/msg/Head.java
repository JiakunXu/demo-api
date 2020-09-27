package com.example.demo.api.dingtalk.ao.msg;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 消息头部内容.
 * 
 * @author JiakunXu
 */
public class Head implements Serializable {

    private static final long serialVersionUID = -2574581895307075078L;

    /**
     * 消息头部的背景颜色。长度限制为8个英文字符，其中前2为表示透明度，后6位表示颜色值。不要添加0x.
     */
    @JSONField(name = "bgcolor")
    private String            bgColor;

    /**
     * 消息的头部标题 (向普通会话发送时有效，向企业会话发送时会被替换为微应用的名字)，长度限制为最多10个字符.
     */
    private String            text;

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
