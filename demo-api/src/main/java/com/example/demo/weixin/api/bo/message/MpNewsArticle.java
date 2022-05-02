package com.example.demo.weixin.api.bo.message;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 * @author JiakunXu
 *
 */
@Getter
@Setter
public class MpNewsArticle implements Serializable {

    private static final long serialVersionUID = 1293536183206475031L;

    @JSONField(name = "article_id")
    private String            articleId;

}
