package com.example.demo.api.dingtalk.ao.msg;

import java.io.Serializable;

/**
 * 卡片消息.
 * 
 * @author JiakunXu
 */
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

    private String 

}
