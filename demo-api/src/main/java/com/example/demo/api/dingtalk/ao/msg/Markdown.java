package com.example.demo.api.dingtalk.ao.msg;

import java.io.Serializable;

/**
 * markdown消息.
 * 
 * @author JiakunXu
 */
public class Markdown implements Serializable {

    private static final long serialVersionUID = -4133015570015522662L;

    /**
     * 首屏会话透出的展示内容.
     */
    private String            title;

    /**
     * markdown格式的消息，建议500字符以内.
     */
    private String            text;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
