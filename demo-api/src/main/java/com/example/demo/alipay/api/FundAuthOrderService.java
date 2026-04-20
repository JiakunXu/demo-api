package com.example.demo.alipay.api;

import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericResponse;

import java.math.BigDecimal;

public interface FundAuthOrderService {

    AlipayOpenApiGenericResponse freeze(String outOrderNo, String outRequestNo, String orderTitle,
                                        BigDecimal amount, String timeoutExpress);

    AlipayOpenApiGenericResponse unfreeze(String authNo, String outRequestNo, BigDecimal amount,
                                          String remark, String extraParam);

    AlipayOpenApiGenericResponse query(String authNo, String outOrderNo, String operationId,
                                       String outRequestNo, String operationType);

    AlipayOpenApiGenericResponse cancel(String authNo, String outOrderNo, String operationId,
                                        String outRequestNo, String remark);

}
