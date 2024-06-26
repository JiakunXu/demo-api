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
public class RoleDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = -4826275632663101733L;

    private BigInteger        id;

    private String            code;

    /**
     * 名称
     */
    private String            name;

    /**
     * 备注
     */
    private String            remark;

    private Integer           order;

    /**
     * 状态
     */
    private String            status;

    public RoleDO() {
    }

    public RoleDO(BigInteger id) {
        this.id = id;
    }

}
