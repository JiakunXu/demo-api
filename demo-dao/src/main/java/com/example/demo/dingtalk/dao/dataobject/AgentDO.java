package com.example.demo.dingtalk.dao.dataobject;

import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;

/**
 * @author JiakunXu
 */
@Getter
@Setter
@ToString
public class AgentDO extends BaseDO {

    private static final long serialVersionUID = -3085683708399250037L;

    private BigInteger        id;

    private String            corpId;

    private String            agentId;

    private String            appKey;

    private String            appSecret;

}
