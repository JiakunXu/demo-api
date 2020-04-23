package com.example.demo.api.bytedance.ao;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
public class BaseResult implements Serializable {

    private static final long serialVersionUID = -7764030576212518819L;

    /**
     * 错误号.
     */
    @JSONField(name = "errcode")
    private String            errCode;

    /**
     * 错误信息.
     */
    @JSONField(name = "errmsg")
    private String            errMsg;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

}
