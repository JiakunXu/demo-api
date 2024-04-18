package com.example.demo.weixin.api.bo.qrcode;

import java.io.Serializable;

import com.alibaba.fastjson2.annotation.JSONField;
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

    @Getter
    @Setter
    public static class ActionInfo implements Serializable {

        private static final long serialVersionUID = 3676011129584429955L;

        private Scene             scene;

        @Getter
        @Setter
        public static class Scene implements Serializable {

            private static final long serialVersionUID = -8212451358776340371L;

            /**
             * 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）.
             */
            @JSONField(name = "scene_id")
            private Integer           sceneId;

            /**
             * 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64.
             */
            @JSONField(name = "scene_str")
            private String            sceneStr;

        }

    }

}
