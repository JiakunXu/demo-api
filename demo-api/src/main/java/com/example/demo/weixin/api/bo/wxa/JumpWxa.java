package com.example.demo.weixin.api.bo.wxa;

import com.alibaba.fastjson.annotation.JSONField;
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
public class JumpWxa implements Serializable {

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
