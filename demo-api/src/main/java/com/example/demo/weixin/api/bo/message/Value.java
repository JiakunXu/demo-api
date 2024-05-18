package com.example.demo.weixin.api.bo.message;

import java.io.Serial;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author JiakunXu
 * 
 */
@Getter
@Setter
public class Value implements Serializable {

    @Serial
    private static final long serialVersionUID = 1535813944895333616L;

    private String            value;

    /**
     * 模板内容字体颜色，不填默认为黑色.
     */
    private String            color;

}
