package com.example.demo.weixin.api.bo.message;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author JiakunXu
 *
 */
@Getter
@Setter
public class TemplateInfo implements Serializable {

    private static final long serialVersionUID = -7104780464279026257L;

    @JSONField(name = "parameter_list")
    private List<Parameter>   parameterList;

}
