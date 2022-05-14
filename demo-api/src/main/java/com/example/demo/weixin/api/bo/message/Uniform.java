package com.example.demo.weixin.api.bo.message;

import com.alibaba.fastjson2.annotation.JSONField;
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
public class Uniform implements Serializable {

    private static final long serialVersionUID = -1761801486337321558L;

    @JSONField(name = "touser")
    private String            toUser;

    @JSONField(name = "weapp_template_msg")
    private WeappTemplateMsg  weappTemplateMsg;

    @JSONField(name = "mp_template_msg")
    private MpTemplateMsg     mpTemplateMsg;

}
