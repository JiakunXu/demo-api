package com.example.demo.weixin.api.bo.device;

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
public class Hardware implements Serializable {

    private static final long serialVersionUID = 5003308650986306234L;

    /**
     * 设备唯一序列号。由厂商分配，长度不能超过128字节。字符只接受数字，大小写字母，下划线（_）和连字符（-）.
     */
    private String            sn;

    /**
     * 设备型号 id ，通过注册设备获得.
     */
    @JSONField(name = "model_id")
    private String            modelId;

}
