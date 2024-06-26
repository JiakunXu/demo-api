package com.example.demo.weixin.api.bo;

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
public class BaseResult implements Serializable {

    @Serial
    private static final long serialVersionUID = -5653302225467940287L;

    @JSONField(name = "errcode")
    private int               errCode;

    @JSONField(name = "errmsg")
    private String            errMsg;

}
