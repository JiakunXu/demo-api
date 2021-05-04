package com.example.demo.framework.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class BaseBo extends BaseParameter {

    private static final long serialVersionUID = 6425935261415820282L;

    @JSONField(serialize = false)
    private Boolean           deleted;

    private String            creator;

    private String            modifier;

    private Date              createTime;

    private Date              updateTime;

}
