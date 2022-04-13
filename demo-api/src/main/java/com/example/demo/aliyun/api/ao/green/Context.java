package com.example.demo.aliyun.api.ao.green;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Context implements Serializable {

    private static final long serialVersionUID = -3082497641746240428L;

    /**
     * 检测文本命中的风险内容上下文内容。如果命中了您自定义的风险文本库，则会返回命中的文本内容（关键词或相似文本）。.
     */
    private String            context;

    /**
     * 命中自定义词库时，才有本字段。取值为创建词库时填写的词库名称。.
     */
    private String            libName;

    /**
     * 命中您自定义文本库时，才会返回该字段，取值为创建风险文本库后系统返回的文本库code。.
     */
    private String            libCode;

    /**
     * 命中行为规则时，才有该字段。可能取值包括：
     * user_id
     * ip
     * umid
     * content
     * similar_content
     * imei
     * imsi.
     */
    private String            ruleType;

}
