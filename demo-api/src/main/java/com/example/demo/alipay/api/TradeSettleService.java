package com.example.demo.alipay.api;

import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericResponse;

public interface TradeSettleService {

    AlipayOpenApiGenericResponse confirm(String outRequestNo, String tradeNo);

}
