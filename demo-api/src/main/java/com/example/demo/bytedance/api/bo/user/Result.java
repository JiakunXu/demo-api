package com.example.demo.bytedance.api.bo.user;

import com.example.demo.bytedance.api.bo.BaseResult;

import lombok.Getter;
import lombok.Setter;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Result extends BaseResult {

    private static final long serialVersionUID = -7764030576212518819L;

    /**
     * 返回非 0.
     */
    private Integer           error;

    /**
     * 错误信息（同 errmsg）.
     */
    private String            message;

}
