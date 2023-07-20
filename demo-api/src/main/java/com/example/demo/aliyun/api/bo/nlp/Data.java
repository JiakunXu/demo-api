package com.example.demo.aliyun.api.bo.nlp;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class Data implements Serializable {

    private static final long serialVersionUID = 323345866361523064L;

    @JSONField(name = "Data")
    private String            data;

    @JSONField(name = "RequestId")
    private String            requestId;

    private List<Result>      result;

    private Boolean           success;

    @Getter
    @Setter
    public static class Result implements Serializable {

        private static final long serialVersionUID = -1203224654151297134L;

        private String            id;

        private String            word;

        private List<String>      tags;

    }

}