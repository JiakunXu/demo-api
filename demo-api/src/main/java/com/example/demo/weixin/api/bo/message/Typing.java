package com.example.demo.weixin.api.bo.message;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 *
 * @author JiakunXu
 *
 */
@Getter
@Setter
public class Typing implements Serializable {

    @Serial
    private static final long serialVersionUID = 4943855854392619653L;

    /**
     * 用户的 OpenID.
     */
    @JSONField(name = "touser")
    private String            toUser;

    /**
     * Typing	对用户下发"正在输入"状态
     * CancelTyping	取消对用户的"正在输入"状态
     */
    private String            command;

}
