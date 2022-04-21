package com.example.demo.weixin.api.bo.qrcode;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author JiakunXu
 * 
 */
@Getter
@Setter
@ToString
public class QrCode implements Serializable {

    private static final long serialVersionUID = 5295096285659716840L;

    /**
     * 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为60秒.
     */
    @JSONField(name = "expire_seconds")
    private Integer           expireSeconds;

    /**
     * 二维码类型，QR_SCENE为临时的整型参数值，QR_STR_SCENE为临时的字符串参数值，QR_LIMIT_SCENE为永久的整型参数值，QR_LIMIT_STR_SCENE为永久的字符串参数值.
     */
    @JSONField(name = "action_name")
    private String            actionName;

    /**
     * 二维码详细信息.
     */
    @JSONField(name = "action_info")
    private ActionInfo        actionInfo;

}
