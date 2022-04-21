package com.example.demo.weixin.api.bo.message;

import com.example.demo.weixin.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

/**
 * @author JiakunXu
 */
@Getter
@Setter
public class Result extends BaseResult {

    private static final long serialVersionUID = -8725706372633271928L;

    private BigInteger        msgid;

}
