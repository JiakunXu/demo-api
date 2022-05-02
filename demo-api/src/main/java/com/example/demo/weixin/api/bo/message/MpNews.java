package com.example.demo.weixin.api.bo.message;

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
public class MpNews implements Serializable {

    private static final long serialVersionUID = 7061489935805032955L;

    @JSONField(name = "media_id")
    private String            mediaId;

}
