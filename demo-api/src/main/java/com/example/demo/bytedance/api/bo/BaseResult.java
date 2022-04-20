package com.example.demo.bytedance.api.bo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class BaseResult implements Serializable {

    private static final long serialVersionUID = -7764030576212518819L;

    /**
     * 错误码.
     */
    @JSONField(name = "err_no")
    private Integer           errNo;

    /**
     * 错误信息.
     */
    @JSONField(name = "err_tips")
    private String            errTips;

    /**
     * 错误号.
     */
    @JSONField(name = "errcode")
    private Integer           errCode;

    /**
     * 错误信息.
     */
    @JSONField(name = "errmsg")
    private String            errMsg;

}
