package com.example.demo.role.api.bo;

import com.example.demo.framework.bo.BaseBO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.math.BigInteger;

@Getter
@Setter
public class RoleMenu extends BaseBO {

    @Serial
    private static final long serialVersionUID = -38498963972946834L;

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
