package com.example.demo.api.weixin.ao;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author JiakunXu
 */
public class WxaCode extends BaseResult {

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

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Boolean getAutoColor() {
        return autoColor;
    }

    public void setAutoColor(Boolean autoColor) {
        this.autoColor = autoColor;
    }

    public LineColor getLineColor() {
        return lineColor;
    }

    public void setLineColor(LineColor lineColor) {
        this.lineColor = lineColor;
    }

    public Boolean getHyaline() {
        return hyaline;
    }

    public void setHyaline(Boolean hyaline) {
        this.hyaline = hyaline;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

}
