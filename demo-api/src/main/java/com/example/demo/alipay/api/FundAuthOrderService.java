package com.example.demo.alipay.api;

import java.math.BigDecimal;

public interface FundAuthOrderService {

    String freeze(String outOrderNo, String outRequestNo, String orderTitle, BigDecimal amount,
                  String timeoutExpress);

    String unfreeze(String authNo, String outRequestNo, BigDecimal amount, String remark,
                    String extraParam);

    String query(String authNo, String outOrderNo, String operationId, String outRequestNo,
                 String operationType);

    String cancel(String authNo, String outOrderNo, String operationId, String outRequestNo,
                  String remark);

}
