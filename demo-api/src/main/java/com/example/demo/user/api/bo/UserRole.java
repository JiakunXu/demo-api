package com.example.demo.user.api.bo;

import com.example.demo.framework.bo.BaseBO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.math.BigInteger;

@Getter
@Setter
public class UserRole extends BaseBO {

    @Serial
    private static final long serialVersionUID = -3320350974637659997L;

    private BigInteger        id;

    /**
     * 员工
     */
    private BigInteger        userId;

    /**
     * 角色
     */
    private BigInteger        roleId;

}
