package com.example.demo.api.aliyun.ao.green;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * @author JiakunXu
 */
public class Return implements Serializable {

    private static final long serialVersionUID = 2873148357896417241L;

    /**
     * 错误描述信息。.
     */
    private String            msg;

    /**
     * 错误码，和HTTP的status code一致。.
     */
    private int               code;

    @JSONField(name = "data")
    private List<Data>        dataList;

    private String            requestId;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
