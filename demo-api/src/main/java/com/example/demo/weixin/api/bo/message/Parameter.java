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
public class Parameter implements Serializable {

    private static final long serialVersionUID = -8739749071365849495L;

    /**
     * 要修改的参数名.
     */
    private String            name;

    /**
     * 修改后的参数值.
     */
    private String            value;

}
