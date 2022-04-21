package com.example.demo.weixin.api.bo.message;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author JiakunXu
 * 
 */
@Getter
@Setter
public class News implements Serializable {

    private static final long serialVersionUID = -3435707328473697197L;

    @JSONField(name = "articles")
    private List<Article>     articleList;

}
