package com.example.demo.alipay.api.bo.user;

import com.example.demo.alipay.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public class PhoneNumber extends BaseResult {

    @Serial
    private static final long serialVersionUID = 8134704376025776455L;

    private String            mobile;

}
