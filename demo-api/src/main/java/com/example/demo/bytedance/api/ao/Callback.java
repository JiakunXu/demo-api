package com.example.demo.bytedance.api.ao;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Callback implements Serializable {

    private static final long serialVersionUID = 5112078717110427827L;

    private String            msg;

    @JSONField(name = "msg_signature")
    private String            msgSignature;

    private String            nonce;

    private String            timestamp;

}
