package com.example.demo.bytedance.api.ao.text;

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
public class Data implements Serializable {

    private static final long serialVersionUID = -6952598677610899377L;

    /**
     * 检测结果-状态码.
     */
    private int               code;

    /**
     * 检测结果- 消息.
     */
    private String            msg;

    /**
     * 检测结果-数据 id.
     */
    @JSONField(name = "data_id")
    private String            dataId;

    /**
     * 检测结果-任务 id.
     */
    @JSONField(name = "task_id")
    private String            taskId;

    /**
     * 检测结果-置信度列表.
     */
    @JSONField(name = "predicts")
    private List<Predict>     predictList;

}
