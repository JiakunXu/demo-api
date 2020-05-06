package com.example.demo.api.bytedance.ao.text;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * @author JiakunXu
 */
public class Log implements Serializable {

    private static final long serialVersionUID = -2775327067303387248L;

    /**
     * 请求 id.
     */
    @JSONField(name = "log_id")
    private String            logId;

    /**
     * 检测结果列表.
     */
    @JSONField(name = "data")
    private List<Data>        dataList;

    // 当 access_token 检验失败时会返回如下信息

    @JSONField(name = "error_id")
    private String            errorId;

    private String            code;

    private String            message;

    private String            exception;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }

    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }
}
