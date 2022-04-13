package com.example.demo.tunnel.dao.dataobject;

import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class TunnelDO extends BaseDO {

    private static final long serialVersionUID = 9197256150530836373L;

    private BigInteger        id;

    private BigInteger        userId;

    private String            tunnelId;

    private String            host;

}
