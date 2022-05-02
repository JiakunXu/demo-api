package com.example.demo.weixin.api.bo.qrcode;

import java.io.Serializable;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author JiakunXu
 * 
 */
@Getter
@Setter
public class ActionInfo implements Serializable {

    private static final long serialVersionUID = 3676011129584429955L;

    private Scene             scene;

}
