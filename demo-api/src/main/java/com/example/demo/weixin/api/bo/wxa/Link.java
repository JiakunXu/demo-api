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
public class Link extends BaseResult {

    @Serial
    private static final long serialVersionUID = -3725782428054121871L;

    private String            link;

}
