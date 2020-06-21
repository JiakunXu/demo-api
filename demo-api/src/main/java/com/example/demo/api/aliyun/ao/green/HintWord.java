package com.example.demo.api.aliyun.ao.green;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
public class HintWord implements Serializable {

    private static final long serialVersionUID = 5057944306650178521L;

    /**
     * 文本命中的系统关键词内容。.
     */
    private String            context;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
