package com.example.demo.weixin.api.bo.qrcode;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author JiakunXu
 * 
 */
@Getter
@Setter
public class Scene implements Serializable {

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
