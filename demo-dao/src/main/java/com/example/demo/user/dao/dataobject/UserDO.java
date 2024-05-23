package com.example.demo.user.dao.dataobject;

import java.io.Serial;
import java.math.BigInteger;

import com.alibaba.fastjson2.annotation.JSONField;
import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 852047823813545755L;

    private BigInteger        id;

    private BigInteger        corpId;

    private String            name;

    private String            username;

    @JSONField(serialize = false)
    private String            password;

    private Boolean           expired;

    private Boolean           locked;

    private Boolean           enabled;

    private String            refreshToken;

    /**
     * user_role
     */
    private BigInteger        roleId;

    private Boolean           exists;

}
