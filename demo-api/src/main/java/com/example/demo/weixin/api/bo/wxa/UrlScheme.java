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

    @Getter
    @Setter
    public static class JumpWxa implements Serializable {

        private static final long serialVersionUID = -8114479188174743438L;

        /**
         * 通过 scheme 码进入的小程序页面路径，必须是已经发布的小程序存在的页面，不可携带 query。path 为空时会跳转小程序主页.
         */
        private String            path;

        /**
         * 通过 scheme 码进入小程序时的 query，最大1024个字符，只支持数字，大小写英文以及部分特殊字符：`!#$&'()*+,/:;=?@-._~%``.
         */
        private String            query;

        /**
         * 要打开的小程序版本。正式版为"release"，体验版为"trial"，开发版为"develop"，仅在微信外打开时生效.
         */
        @JSONField(name = "env_version")
        private String            envVersion;

    }

}
