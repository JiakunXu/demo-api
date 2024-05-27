package com.example.demo.tunnel.api.bo;

import com.example.demo.framework.bo.BaseBO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.math.BigInteger;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Tunnel extends BaseBO {

    @Serial
    private static final long serialVersionUID = -2952991612958654453L;

    private BigInteger        id;

    private BigInteger        userId;

    private String            tunnelId;

    private String            host;

}
