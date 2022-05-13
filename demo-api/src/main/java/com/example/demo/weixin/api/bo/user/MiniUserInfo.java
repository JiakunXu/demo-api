package com.example.demo.weixin.api.bo.user;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class MiniUserInfo implements Serializable {

    private static final long serialVersionUID = -5265739422265707773L;

    private String            openId;

    private String            nickName;

    private Integer           gender;

    private String            city;

    private String            province;

    private String            country;

    private String            avatarUrl;

    private String            unionId;

    private Watermark         watermark;

}
