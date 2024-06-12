package com.example.demo.alipay.api.bo.message;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = 6605884389279763367L;

    private String            notifyId;

    private String            appId;

    private String            bizContent;

    private String            charset;

    private String            msgMethod;

    private String            sign;

    private String            signType;

    private String            utcTimestamp;

    private String            version;

}
