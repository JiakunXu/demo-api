package com.example.demo.aliyun.api.bo.green;

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

}
