package com.example.demo.bytedance.api.bo.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 7199131797406817934L;

    /**
     * 用户昵称.
     */
    private String            nickName;

    /**
     * 用户头像网络地址.
     */
    private String            avatarUrl;

    /**
     * 用户性别，0: 未知；1:男性；2:女性.
     */
    private Integer           gender;

    /**
     * 用户所在城市.
     */
    private String            city;

    /**
     * 用户所在省份.
     */
    private String            province;

    /**
     * 用户所在国家.
     */
    private String            country;

    /**
     * 用户 openId.
     */
    private String            openId;

    /**
     * 敏感数据水印.
     */
    private Watermark         watermark;

}
