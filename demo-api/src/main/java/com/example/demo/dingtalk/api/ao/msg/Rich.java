package com.example.demo.dingtalk.api.ao.msg;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 单行富文本信息.
 *
 * @author JiakunXu
 */
@Getter
@Setter
public class Rich implements Serializable {

    private static final long serialVersionUID = -5246429195106842539L;

    /**
     * 单行富文本信息的数目.
     */
    private String            num;

    /**
     * 单行富文本信息的单位.
     */
    private String            unit;

}
