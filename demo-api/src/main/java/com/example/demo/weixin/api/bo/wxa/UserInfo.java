package com.example.demo.weixin.api.bo.wxa;

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
public class UserInfo implements Serializable {

    private static final long serialVersionUID = -5683639701731292630L;

    /**
     * 小程序appid.
     */
    private String            appid;

    /**
     * 用户的openid.
     */
    private String            openid;

    /**
     * 场景值，0:注册，1:营销作弊.
     */
    private Integer           scene;

    /**
     * 用户手机号.
     */
    @JSONField(name = "mobile_no")
    private String            mobileNo;

    /**
     * 用户访问源ip.
     */
    @JSONField(name = "client_ip")
    private String            clientIp;

    /**
     * 用户邮箱地址.
     */
    @JSONField(name = "email_address")
    private String            emailAddress;

    /**
     * 额外补充信息.
     */
    @JSONField(name = "extended_info")
    private String            extendedInfo;

    /**
     * false：正式调用，true：测试调用.
     */
    @JSONField(name = "is_test")
    private Boolean           test;

}
