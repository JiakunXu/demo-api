package com.example.demo.api.bytedance.ao.text;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author JiakunXu
 */
@Getter
@Setter
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

}
