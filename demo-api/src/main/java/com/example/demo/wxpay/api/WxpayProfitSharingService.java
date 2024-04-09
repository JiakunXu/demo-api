package com.example.demo.wxpay.api;

import com.example.demo.wxpay.api.bo.WxpayProfitSharing;

public interface WxpayProfitSharingService {

    WxpayProfitSharing getWxpayProfitSharing(String transactionId, String outOrderNo);

    WxpayProfitSharing getWxpayProfitSharing(String subMchid, String transactionId,
                                             String outOrderNo);

    WxpayProfitSharing getWxpayProfitSharing(String serialNumber, String nonce, String timestamp,
                                             String signature, String body);

    WxpayProfitSharing insertWxpayProfitSharing(WxpayProfitSharing wxpayProfitSharing);

    WxpayProfitSharing updateWxpayProfitSharing(String orderId,
                                                WxpayProfitSharing wxpayProfitSharing);

}
