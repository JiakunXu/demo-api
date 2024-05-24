package com.example.demo.alipay.api.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class BaseResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 5735591826759505627L;

    private String            code;

    private String            msg;

    private String            subCode;

    private String            subMsg;

}
