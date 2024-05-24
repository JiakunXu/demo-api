package com.example.demo.log.dao.dataobject;

import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@ToString
public class LoginLogDO extends BaseDO {

    private static final long serialVersionUID = -4810978561182544347L;

    private BigInteger        id;

    /**
     * 登录账号
     */
    private String            username;

    /**
     * ip
     */
    private String            ip;

    /**
     * 登录地址
     */
    private String            ipAddr;

    /**
     * 登录时间
     */
    private Date              loginTime;

    /**
     * 状态
     */
    private String            status;

    /**
     * 错误消息
     */
    private String            errMsg;

}
