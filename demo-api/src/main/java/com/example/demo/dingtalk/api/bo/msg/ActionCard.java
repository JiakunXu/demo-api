package com.example.demo.dingtalk.api.bo.msg;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 卡片消息.
 * 
 * @author JiakunXu
 */
@Getter
@Setter
public class ActionCard implements Serializable {

    private static final long serialVersionUID = 4668443912139891110L;

    /**
     * 透出到会话列表和通知的文案，最长64个字符.
     */
    private String            title;

    /**
     * 消息内容，支持markdown，语法参考标准markdown语法。建议1000个字符以内.
     */
    private String            markdown;

    /**
     * 使用整体跳转ActionCard样式时的标题，必须与single_url同时设置，最长20个字符.
     */
    @JSONField(name = "single_title")
    private String            singleTitle;

    /**
     * 消息点击链接地址，当发送消息为小程序时支持小程序跳转链接，最长500个字符.
     */
    @JSONField(name = "single_url")
    private String            singleUrl;

    /**
     * 使用独立跳转ActionCard样式时的按钮排列方式，竖直排列(0)，横向排列(1)；必须与btn_json_list同时设置.
     */
    @JSONField(name = "btn_orientation")
    private String            btnOrientation;

    /**
     * 使用独立跳转ActionCard样式时的按钮列表；必须与btn_orientation同时设置.
     */
    @JSONField(name = "btn_json_list")
    private List<Btn>         btnList;

}
