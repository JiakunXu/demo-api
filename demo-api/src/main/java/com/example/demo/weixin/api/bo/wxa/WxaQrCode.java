package com.example.demo.weixin.api.bo.wxa;

import com.example.demo.weixin.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;

/**
 * @author JiakunXu
 */
@Getter
@Setter
@ToString
public class WxaQrCode extends BaseResult {

    @Serial
    private static final long serialVersionUID = 2888579501128777299L;

    /**
     * 扫码进入的小程序页面路径，最大长度 128 字节，不能为空；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"，即可在 wx.getLaunchOptionsSync 接口中的 query 参数获取到 {foo:"bar"}.
     */
    private String            path;

    /**
     * 二维码的宽度，单位 px。最小 280px，最大 1280px.
     */
    private Integer           width;

    /**
     * 数据类型 (MIME Type).
     */
    private String            contentType;

    /**
     * 数据 Buffer.
     */
    private byte[]            buffer;

}
