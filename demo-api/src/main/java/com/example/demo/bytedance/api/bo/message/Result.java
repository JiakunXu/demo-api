package com.example.demo.bytedance.api.bo.message;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Result implements Serializable {

    @Serial
    private static final long serialVersionUID = 9129556361978461259L;

    /**
     * 错误码.
     */
    @JSONField(name = "errno")
    private int               errNo;

    /**
     * 描述信息.
     */
    private String            msg;

}
