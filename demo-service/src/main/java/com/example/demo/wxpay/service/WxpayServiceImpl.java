package com.example.demo.wxpay.service;

import com.alibaba.fastjson2.JSON;
import com.example.demo.wxpay.api.JsapiService;
import com.example.demo.wxpay.api.PartnerJsapiService;
import com.example.demo.wxpay.api.WxpayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WxpayServiceImpl implements WxpayService {

    @Autowired
    private JsapiService        jsapiService;

    @Autowired
    private PartnerJsapiService partnerJsapiService;

    @Value("${wxpay.app.id}")
    private String              appid;

    @Value("${wxpay.merchant.id}")
    private String              mchid;

    @Value("${wxpay.notify.url}")
    private String              notifyUrl;

    @Value("${wxpay.partner.app.id}")
    private String              spAppid;

    @Value("${wxpay.partner.merchant.id}")
    private String              spMchid;

    @Value("${wxpay.partner.notify.url}")
    private String              spNotifyUrl;

    @Override
    public String build(String description, String outTradeNo, String timeExpire, String attach,
                        int totalFee, String openid) {
        com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse response = jsapiService
            .prepayWithRequestPayment(appid, mchid, description, outTradeNo, timeExpire, attach,
                notifyUrl, totalFee, openid);

        return JSON.toJSONString(response);
    }

    @Override
    public String build(String subMchid, String description, String outTradeNo, String timeExpire,
                        String attach, int totalFee, String openid) {
        com.wechat.pay.java.service.partnerpayments.jsapi.model.PrepayWithRequestPaymentResponse response = partnerJsapiService
            .prepayWithRequestPayment(spAppid, spMchid, subMchid, description, outTradeNo,
                timeExpire, attach, spNotifyUrl, totalFee, openid);

        return JSON.toJSONString(response);
    }

    @Override
    public void close(String outTradeNo) {
        jsapiService.closeOrder(mchid, outTradeNo);
    }

    @Override
    public void close(String subMchid, String outTradeNo) {
        partnerJsapiService.closeOrder(spMchid, subMchid, outTradeNo);
    }

}
