package com.example.demo.api.dingtalk.ao.msg;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 链接消息.
 *
 * @author JiakunXu
 */
@Getter
@Setter
public class Link implements Serializable {

    private static final long serialVersionUID = -7430554786771839102L;

    /**
     * 消息点击链接地址，当发送消息为小程序时支持小程序跳转链接.
     */
    private String            messageUrl;

    /**
     * 图片地址。可以通过媒体文件接口上传图片获取。.
     */
    private String            picUrl;

    /**
     * 消息标题，建议100字符以内.
     */
    private String            title;

    /**
     * 消息描述，建议500字符以内.
     */
    private String            text;

}
