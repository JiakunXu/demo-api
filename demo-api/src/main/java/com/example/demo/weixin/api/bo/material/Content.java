package com.example.demo.weixin.api.bo.material;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author JiakunXu
 *
 */
@Getter
@Setter
public class Content implements Serializable {

    private static final long serialVersionUID = 8487304781166093559L;

    @JSONField(name = "news_item")
    private List<NewsItem>    newsItemList;

}
