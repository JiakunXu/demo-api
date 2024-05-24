package com.example.demo.alipay.api.bo.user;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class UserInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 7907696119731469351L;

    /**
     * 支付宝用户的userId。
     */
    @JSONField(name = "user_id")
    private String            userId;

    /**
     * 用户头像地址。
     */
    private String            avatar;

    /**
     * 市名称。
     */
    private String            city;

    /**
     * 用户昵称。
     */
    @JSONField(name = "nick_name")
    private String            nickName;

    /**
     * 省份名称。
     */
    private String            province;

    /**
     * 性别。
     */
    private String            gender;

    private String            mobile;

}
