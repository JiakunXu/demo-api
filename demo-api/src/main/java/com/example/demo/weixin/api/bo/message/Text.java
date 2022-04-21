package com.example.demo.weixin.api.bo.message;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 
 * @author JiakunXu
 * 
 */
@Getter
@Setter
public class Text implements Serializable {

    private static final long serialVersionUID = 552698313157925708L;

    /**
     * 消息内容.
     */
    private String            content;

}
