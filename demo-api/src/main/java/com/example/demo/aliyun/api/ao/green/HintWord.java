package com.example.demo.aliyun.api.ao.green;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class HintWord implements Serializable {

    private static final long serialVersionUID = 5057944306650178521L;

    /**
     * 文本命中的系统关键词内容。.
     */
    private String            context;

}
