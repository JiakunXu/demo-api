package com.example.demo.api.aliyun.ao.green;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {

    private static final long serialVersionUID = 2367280980327731603L;

    /**
     * 检测场景，和调用请求中的场景对应。.
     */
    private String            scene;

    /**
     * 建议用户执行的操作，取值范围：
     * pass：文本正常
     * review：需要人工审核
     * block：文本违规，可以直接删除或者做限制处理.
     */
    private String            suggestion;

    /**
     * 检测结果的分类，与具体的scene对应。取值范围请参见scene 和 label说明。.
     */
    private String            label;

    /**
     * 	结果为该分类的概率，取值范围为[0.00-100.00]。值越高，表示越有可能属于该分类。.
     */
    private float             rate;

    /**
     * 命中风险的详细信息，一条文本可能命中多条风险详情。具体结构描述请参见detail。.
     */
    @JSONField(name = "details")
    private List<Detail>      detailList;

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public List<Detail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<Detail> detailList) {
        this.detailList = detailList;
    }
}
