package com.example.demo.wxpay.api;

import com.wechat.pay.java.service.refund.model.Refund;

public interface RefundService {

    Refund create(String outTradeNo, String outRefundNo, String reason, String notifyUrl,
                  Long refund, Long total);

    Refund create(String subMchid, String outTradeNo, String outRefundNo, String reason,
                  String notifyUrl, Long refund, Long total);

    Refund queryByOutRefundNo(String outRefundNo);

    Refund queryByOutRefundNo(String subMchid, String outRefundNo);

}
