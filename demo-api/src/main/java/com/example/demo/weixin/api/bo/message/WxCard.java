package com.example.demo.weixin.api.bo.message;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 * @author JiakunXu
 *
 */
@Getter
@Setter
public class WxCard implements Serializable {

    private static final long serialVersionUID = 5035057128920912659L;

    @JSONField(name = "card_id")
    private String            cardId;

}
