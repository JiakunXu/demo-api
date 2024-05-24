package com.example.demo.log.api.bo;

import com.example.demo.framework.bo.BaseBO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
public class OperLog extends BaseBO {

    @Serial
    private static final long serialVersionUID = -4732081383657654965L;

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

    public enum Status {
                        /**
                         * 成功/失败
                         */
                        SUCCESS("S", "成功"), FAIL("F", "失败");

        public final String value;

        public final String desc;

        Status(String value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

}
