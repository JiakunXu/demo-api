package com.example.demo.api.dingtalk.ao.msg;

import java.io.Serializable;

/**
 * 消息体的表单.
 * 
 * @author JiakunXu
 */
public class Form implements Serializable {

    private static final long serialVersionUID = -7216945136571863045L;

    /**
     * 消息体的关键字.
     */
    private String            key;

    /**
     * 消息体的关键字对应的值.
     */
    private String            value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
