package com.example.demo.api.tunnel.bo;

import com.example.demo.framework.bo.BaseBo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Tunnel extends BaseBo {

    private static final long serialVersionUID = -2952991612958654453L;

    private BigInteger        id;

    private BigInteger        userId;

    private String            tunnelId;

    private String            host;

}
