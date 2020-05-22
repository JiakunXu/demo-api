package com.example.demo.api.bytedance.ao;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
public class SendResult implements Serializable {

    private static final long serialVersionUID = 9129556361978461259L;

    /**
     * 错误码.
     */
    @JSONField(name = "errno")
    private String            errNo;

    /**
     * 描述信息.
     */
    private String            msg;

    public String getErrNo() {
        return errNo;
    }

    public void setErrNo(String errNo) {
        this.errNo = errNo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
