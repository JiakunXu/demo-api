package com.example.demo.weixin.api.bo.wxa;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
@ToString
public class Page implements Serializable {

    private static final long serialVersionUID = -3346507682950596722L;

    /**
     * 通过 Short Link 进入的小程序页面路径，必须是已经发布的小程序存在的页面，可携带 query，最大1024个字符
     */
    @JSONField(name = "page_url")
    private String            pageUrl;

    /**
     * 页面标题，不能包含违法信息，超过20字符会用... 截断代替.
     */
    @JSONField(name = "page_title")
    private String            pageTitle;

    /**
     * 生成的 Short Link 类型，短期有效：false，永久有效：true.
     */
    @JSONField(name = "is_permanent")
    private Boolean           permanent;

}
