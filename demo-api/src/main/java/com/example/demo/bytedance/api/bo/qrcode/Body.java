package com.example.demo.bytedance.api.bo.qrcode;

import java.io.Serial;
import java.io.Serializable;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author JiakunXu
 */
@Getter
@Setter
@ToString
public class Body implements Serializable {

    @Serial
    private static final long serialVersionUID = -4773047490509322993L;

    /**
     * 服务端 API 调用标识，获取方法.
     */
    @JSONField(name = "access_token")
    private String            accessToken;

    /**
     * 是打开二维码的字节系 app 名称，默认为今日头条，取值如下表所示.
     */
    @JSONField(name = "appname")
    private String            appName;

    /**
     * 小程序/小游戏启动参数，小程序则格式为 encode({path}?{query})，小游戏则格式为 JSON 字符串，默认为空.
     */
    private String            path;

    /**
     * 二维码宽度，单位 px，最小 280px，最大 1280px，默认为 430px.
     */
    private Integer           width;

    /**
     * 二维码线条颜色，默认为黑色.
     */
    @JSONField(name = "line_color")
    private LineColor         lineColor;

    /**
     * 二维码背景颜色，默认为白色.
     */
    private Background        background;

    /**
     * 是否展示小程序/小游戏 icon，默认不展示.
     */
    @JSONField(name = "set_icon")
    private Boolean           setIcon;

    @Getter
    @Setter
    public static class LineColor implements Serializable {

        @Serial
        private static final long serialVersionUID = -4682306844587994906L;

        private int               r;

        private int               g;

        private int               b;

    }

    @Getter
    @Setter
    public static class Background implements Serializable {

        @Serial
        private static final long serialVersionUID = -4682306844587994906L;

        private int               r;

        private int               g;

        private int               b;

    }

}
