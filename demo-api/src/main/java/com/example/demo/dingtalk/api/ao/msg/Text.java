package com.example.demo.dingtalk.api.ao.msg;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 文本消息.
 * 
 * @author JiakunXu
 */
@Getter
@Setter
public class Text implements Serializable {

    private static final long serialVersionUID = -936824187442532790L;

    /**
     * 消息内容，建议500字符以内.
     */
    private String            content;

}
