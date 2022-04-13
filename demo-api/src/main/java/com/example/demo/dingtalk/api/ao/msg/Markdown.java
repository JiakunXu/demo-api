package com.example.demo.dingtalk.api.ao.msg;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * markdown消息.
 * 
 * @author JiakunXu
 */
@Getter
@Setter
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

}
