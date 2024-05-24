package com.example.demo.alipay.api.bo.user;

import com.example.demo.alipay.api.bo.BaseResult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneNumber extends BaseResult {

    private static final long serialVersionUID = 8134704376025776455L;

    private String            mobile;

}
