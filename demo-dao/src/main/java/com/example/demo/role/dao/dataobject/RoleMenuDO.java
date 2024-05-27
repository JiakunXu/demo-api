package com.example.demo.role.dao.dataobject;

import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.math.BigInteger;

@Getter
@Setter
@ToString
public class RoleMenuDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = -5876006806537431915L;

    private BigInteger        id;

    /**
     * 角色
     */
    private BigInteger        roleId;

    /**
     * 菜单
     */
    private BigInteger        menuId;

}
