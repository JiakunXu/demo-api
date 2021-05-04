package com.example.demo.api.dingtalk.ao.msg;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 消息体的表单.
 * 
 * @author JiakunXu
 */
@Getter
@Setter
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

}
