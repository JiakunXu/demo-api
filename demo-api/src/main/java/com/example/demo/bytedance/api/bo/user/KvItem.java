package com.example.demo.bytedance.api.bo.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class KvItem implements Serializable {

    private static final long serialVersionUID = 4109633365245598875L;

    /**
     * 键.
     */
    private String            key;

    /**
     * 值.
     */
    private String            value;

}
