package com.example.demo.user.dao.dataobject;

import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.math.BigInteger;

@Getter
@Setter
@ToString
public class UserRoleDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = -5384472265511972841L;

    private BigInteger        id;

    /**
     * 员工
     */
    private BigInteger        userId;

    /**
     * 角色
     */
    private BigInteger        roleId;

    private BigInteger[]      userIds;

    /**
     * role.code
     */
    private String            code;

    /**
     * role.status
     */
    private String            status;

}
