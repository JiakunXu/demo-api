package com.example.demo.api.dingtalk.ao.msg;

import java.io.Serializable;

/**
 * 文本消息.
 * 
 * @author JiakunXu
 */
public class Text implements Serializable {

    private static final long serialVersionUID = -936824187442532790L;

    /**
     * 消息内容，建议500字符以内.
     */
    private String            content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
