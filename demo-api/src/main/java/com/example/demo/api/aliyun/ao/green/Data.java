package com.example.demo.api.aliyun.ao.green;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

/**
 * @author JiakunXu
 */
public class Data implements Serializable {

    private static final long serialVersionUID = -9188611355572141525L;

    /**
     * 错误码，和HTTP的status code一致。.
     */
    private int               code;

    /**
     * 错误描述信息。.
     */
    private String            msg;

    /**
     * 检测对象对应的数据ID。.
     */
    private String            dataId;

    /**
     * 该检测任务的ID。.
     */
    private String            taskId;

    /**
     * 对应请求的内容。.
     */
    private String            content;

    /**
     * 如果检测文本命中您自定义关键词词库中的词，该字段会返回，并将命中的关键词替换为“*”。.
     */
    private String            filteredContent;

    /**
     * 返回结果。调用成功时（code=200），返回结果中包含一个或多个元素。每个元素是个结构体，具体结构描述请参见result。.
     */
    @JSONField(name = "results")
    private List<Result>      resultList;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilteredContent() {
        return filteredContent;
    }

    public void setFilteredContent(String filteredContent) {
        this.filteredContent = filteredContent;
    }

    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }
}
