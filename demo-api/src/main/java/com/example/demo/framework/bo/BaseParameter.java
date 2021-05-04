package com.example.demo.framework.bo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class BaseParameter implements Serializable {

    private static final long serialVersionUID = -2053113219884447756L;

    private String            search;

    private String            gmtStart;

    private String            gmtEnd;

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
    @JSONField(serialize = false)
    private int               pageNo;

    @JSONField(serialize = false)
    private int               pageSize;

    @JSONField(serialize = false)
    private int               offset;

    public int getOffset() {
        return (this.pageNo - 1) * this.pageSize;
    }

}
