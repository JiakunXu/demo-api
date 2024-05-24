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
public class OperLogDO extends BaseDO {

    private static final long serialVersionUID = -5521798017790714164L;

    private BigInteger        id;

    private String            clazz;

    /**
     * 请求uri
     */
    private String            requestUri;

    /**
     * 请求方式
     */
    private String            requestMethod;

    /**
     * 请求参数
     */
    private String            requestParams;

    /**
     * 操作人员
     */
    private String            operator;

    /**
     * 主机地址
     */
    private String            ip;

    /**
     * ip_addr
     */
    private String            ipAddr;

    /**
     * 操作时间
     */
    private Date              operTime;

    private String            module;

    /**
     * 操作描述
     */
    private String            desc;

    /**
     * 状态
     */
    private String            status;

    /**
     * 错误消息
     */
    private String            errMsg;

    private BigInteger        corpId;

    public OperLogDO() {
    }

    public OperLogDO(BigInteger id) {
        this.id = id;
    }

}
