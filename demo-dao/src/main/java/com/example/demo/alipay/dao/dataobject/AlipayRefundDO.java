package com.example.demo.alipay.dao.dataobject;

import com.example.demo.framework.dataobject.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.math.BigInteger;

@Getter
@Setter
@ToString
public class AlipayRefundDO extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1715441966296002388L;

    private BigInteger        id;

    private String            outTradeNo;

    private String            outRequestNo;

    private String            refund;

}
