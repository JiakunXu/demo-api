package com.example.demo.api.bytedance.ao.text;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
public class Predict implements Serializable {

    private static final long serialVersionUID = -6768050574984180332L;

    /**
     * 检测结果-置信度-服务/目标.
     */
    private String            target;

    /**
     * 检测结果-置信度-模型/标签.
     */
    @JSONField(name = "model_name")
    private String            modelName;

    /**
     * 检测结果-置信度-概率，值为 0 或者 1，当值为 1 时表示检测的文本包含违法违规内容.
     */
    private int               prob;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getProb() {
        return prob;
    }

    public void setProb(int prob) {
        this.prob = prob;
    }

}
