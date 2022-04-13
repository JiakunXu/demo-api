package com.example.demo.bytedance.api.ao.text;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
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

}
