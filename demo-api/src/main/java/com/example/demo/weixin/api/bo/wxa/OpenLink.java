package com.example.demo.weixin.api.bo.wxa;

import com.example.demo.weixin.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class OpenLink extends BaseResult {

    @Serial
    private static final long serialVersionUID = -3126970556523772318L;

    private String            openLink;

}
