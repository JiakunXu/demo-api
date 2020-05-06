package com.example.demo.api.bytedance.ao.text;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * @author JiakunXu
 */
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<Predict> getPredictList() {
        return predictList;
    }

    public void setPredictList(List<Predict> predictList) {
        this.predictList = predictList;
    }

}
