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
public class WxaCode implements Serializable {

    private static final long serialVersionUID = 2805710848332841282L;

    /**
     * 最大32个可见字符，只支持数字，大小写英文以及部分特殊字符：!#$&'()*+,/:;=?@-._~，其它字符请自行编码为合法字符（因不支持%，中文无法使用 urlencode 处理，请使用其他编码方式.
     */
    private String            scene;

    /**
     * 必须是已经发布的小程序存在的页面（否则报错），例如 pages/index/index, 根路径前不要填加 /,不能携带参数（参数请放在scene字段里），如果不填写这个字段，默认跳主页面.
     */
    private String            page;

    /**
     * 默认是true，检查page 是否存在，为 true 时 page 必须是已经发布的小程序存在的页面（否则报错）；为 false 时允许小程序未发布或者 page 不存在， 但page 有数量上限（60000个）请勿滥用
     */
    @JSONField(name = "check_path")
    private Boolean           checkPath;

    /**
     * 要打开的小程序版本。正式版为 "release"，体验版为 "trial"，开发版为 "develop"。默认是正式版
     */
    @JSONField(name = "env_version")
    private String            envVersion;

    /**
     * 二维码的宽度，单位 px，最小 280px，最大 1280px.
     */
    private Integer           width;

    /**
     * 自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调，默认 false.
     */
    @JSONField(name = "auto_color")
    private Boolean           autoColor;

    /**
     * auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"} 十进制表示.
     */
    @JSONField(name = "line_color")
    private LineColor         lineColor;

    /**
     * 是否需要透明底色，为 true 时，生成透明底色的小程序.
     */
    @JSONField(name = "is_hyaline")
    private Boolean           hyaline;

    /**
     * 数据类型 (MIME Type).
     */
    private String            contentType;

    /**
     * 数据 Buffer.
     */
    private byte[]            buffer;

    @Getter
    @Setter
    public static class LineColor implements Serializable {

        private static final long serialVersionUID = -4682306844587994906L;

        private int               r;

        private int               g;

        private int               b;

    }

}
