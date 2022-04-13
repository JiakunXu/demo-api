package com.example.demo.framework.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class BaseBo implements Serializable {

    private static final long serialVersionUID = 6425935261415820282L;

    private String            search;

    private String            startDate;

    private String            endDate;

    /**
     * @see com.example.demo.framework.config.JsonConfigurer
     */
    private String[]          codes;

    /**
     * 排序字段.
     */
    private String            sort;

    /**
     * 排序类型.
     */
    private String            dir;

    /**
     * 分页号，从1开始.
     */
    private Integer           pageNo;

    private Integer           pageSize;

}
