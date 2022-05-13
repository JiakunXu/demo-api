package com.example.demo.dingtalk.api.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Agent implements Serializable {

    private static final long serialVersionUID = 2872975505231242077L;

    private BigInteger        id;

    private String            corpId;

    private String            agentId;

    private String            appKey;

    private String            appSecret;

    private String            aesKey;

    private String            token;

}
