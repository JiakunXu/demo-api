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
public class Detail implements Serializable {

    private static final long serialVersionUID = -4343071563206815959L;

    /**
     * 文本命中风险的分类，与具体的scene对应。取值范围请参见scene 和 label说明。.
     */
    private String            label;

    /**
     * 命中该风险的上下文信息。具体结构描述请参见context。.
     */
    @JSONField(name = "contexts")
    private List<Context>     contextList;

    /**
     * 文本命中的关键词信息，用于提示您违规的原因，可能会返回多个命中的关键词。具体结构描述请参见hintWord。.
     */
    @JSONField(name = "hintWords")
    private List<HintWord>    hintWordList;

}
