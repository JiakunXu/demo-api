package com.example.demo.weixin.api.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Jsapi implements Serializable {

    private static final long serialVersionUID = -1941311743234502564L;

    /**
     * 公众号的唯一标识.
     */
    private String            appId;

    /**
     * 生成签名的时间戳.
     */
    private String            timestamp;

    /**
     * 生成签名的随机串.
     */
    private String            nonceStr;

    /**
     * 签名.
     */
    private String            signature;

}
