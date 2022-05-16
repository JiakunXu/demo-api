package com.example.demo.weixin.api.bo.menu;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author JiakunXu
 *
 */
@Getter
@Setter
@ToString
public class Menu implements Serializable {

    private static final long serialVersionUID = -3786757350633940882L;

    @JSONField(name = "button")
    private List<Button>      buttonList;

}
