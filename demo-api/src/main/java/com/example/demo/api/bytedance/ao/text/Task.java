package com.example.demo.api.bytedance.ao.text;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
public class Task implements Serializable {

    private static final long serialVersionUID = -6449251347845073879L;

    /**
     * 检测的文本内容.
     */
    private String            content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
