package com.example.demo.user.api.bo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.example.demo.framework.bo.BaseBO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class User extends BaseBO {

    private static final long serialVersionUID = 4850882151993837337L;

    private BigInteger        id;

    private String            name;

    private String            username;

    @JSONField(serialize = false)
    private String            password;

    private Boolean           expired;

    private Boolean           locked;

    private Boolean           enabled;

    private String            refreshToken;

}
