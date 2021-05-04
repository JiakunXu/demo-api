package com.example.demo.api.dingtalk.ao.msg;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * OA消息.
 *
 * @author JiakunXu
 */
@Getter
@Setter
public class Oa implements Serializable {

    private static final long serialVersionUID = -5096367706138112295L;

    @JSONField(name = "message_url")
    private String            messageUrl;

    @JSONField(name = "pc_message_url")
    private String            pcMessageUrl;

    /**
     * 消息头部内容.
     */
    private Head              head;

    /**
     * 消息体.
     */
    private Body              body;

}
