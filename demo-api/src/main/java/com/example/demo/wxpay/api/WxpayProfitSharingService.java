package com.example.demo.wxpay.api;

import com.example.demo.wxpay.api.bo.WxpayProfitSharing;

public interface WxpayProfitSharingService {

    WxpayProfitSharing getProfitSharing(String transactionId, String outOrderNo);

    WxpayProfitSharing getProfitSharing(String subMchid, String transactionId, String outOrderNo);

    WxpayProfitSharing getProfitSharing(String serialNumber, String nonce, String timestamp,
                                        String signature, String body);

    WxpayProfitSharing insertProfitSharing(WxpayProfitSharing profitSharing);

    WxpayProfitSharing updateProfitSharing(String orderId, WxpayProfitSharing profitSharing);

}
