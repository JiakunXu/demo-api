package com.example.demo.weixin.api.ao;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class BaseResult implements Serializable {

    private static final long serialVersionUID = -5653302225467940287L;

    @JSONField(name = "errcode")
    private String            errCode;

    @JSONField(name = "errmsg")
    private String            errMsg;

}
