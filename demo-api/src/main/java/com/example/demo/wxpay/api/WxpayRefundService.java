package com.example.demo.wxpay.api;

import com.example.demo.wxpay.api.bo.WxpayRefund;

public interface WxpayRefundService {

    WxpayRefund getWxpayRefund(String outRefundNo);

    WxpayRefund getWxpayRefund(String subMchid, String outRefundNo);

    WxpayRefund getWxpayRefundV1(String serialNumber, String nonce, String timestamp,
                                 String signature, String body);

    WxpayRefund getWxpayRefundV2(String serialNumber, String nonce, String timestamp,
                                 String signature, String body);

    WxpayRefund insertWxpayRefund(WxpayRefund wxpayRefund);

    WxpayRefund updateWxpayRefund(String refundId, WxpayRefund wxpayRefund);

}
