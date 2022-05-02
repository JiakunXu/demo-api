package com.example.demo.weixin.api.bo.wxa;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
@ToString
public class UrlScheme implements Serializable {

    private static final long serialVersionUID = -9098429045150610337L;

    /**
     * 跳转到的目标小程序信息.
     */
    @JSONField(name = "jump_wxa")
    private JumpWxa           jumpWxa;

    /**
     * 到期失效的 scheme 码失效类型，失效时间：0，失效间隔天数：1.
     */
    @JSONField(name = "expire_type")
    private Integer           expireType;

    /**
     * 到期失效的 scheme 码的失效时间，为 Unix 时间戳。生成的到期失效 scheme 码在该时间前有效。最长有效期为30天。expire_type 为 0 时必填.
     */
    @JSONField(name = "expire_time")
    private Integer           expireTime;

    /**
     * 到期失效的 scheme 码的失效间隔天数。生成的到期失效 scheme 码在该间隔时间到达前有效。最长间隔天数为30天。 expire_type 为 1 时必填.
     */
    @JSONField(name = "expire_interval")
    private Integer           expireInterval;

}
