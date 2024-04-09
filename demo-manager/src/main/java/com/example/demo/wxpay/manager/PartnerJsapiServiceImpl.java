package com.example.demo.wxpay.manager;

import com.example.demo.wxpay.api.PartnerJsapiService;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.exception.HttpException;
import com.wechat.pay.java.core.exception.MalformedMessageException;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.service.partnerpayments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.partnerpayments.jsapi.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartnerJsapiServiceImpl implements PartnerJsapiService {

    private static final Logger logger = LoggerFactory.getLogger(PartnerJsapiServiceImpl.class);

    @Autowired(required = false)
    private Config              partnerConfig;

    @Override
    public PrepayWithRequestPaymentResponse prepayWithRequestPayment(String spAppid, String spMchid,
                                                                     String subMchid,
                                                                     String description,
                                                                     String outTradeNo,
                                                                     String timeExpire,
                                                                     String attach,
                                                                     String notifyUrl,
                                                                     Integer total,
                                                                     String spOpenid) {
        JsapiServiceExtension service = new JsapiServiceExtension.Builder().config(partnerConfig)
            .build();

        PrepayRequest request = new PrepayRequest();

        request.setSpAppid(spAppid);
        request.setSpMchid(spMchid);
        request.setSubMchid(subMchid);
        request.setDescription(description);
        request.setOutTradeNo(outTradeNo);
        request.setTimeExpire(timeExpire);
        request.setAttach(attach);
        request.setNotifyUrl(notifyUrl);

        SettleInfo settleInfo = new SettleInfo();
        settleInfo.setProfitSharing(Boolean.TRUE);
        request.setSettleInfo(settleInfo);

        Amount amount = new Amount();
        amount.setTotal(total);
        request.setAmount(amount);

        Payer payer = new Payer();
        payer.setSpOpenid(spOpenid);
        request.setPayer(payer);

        try {
            return service.prepayWithRequestPayment(request, spAppid);
        } catch (HttpException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException("发送HTTP请求失败");
        } catch (ServiceException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getErrorMessage());
        } catch (MalformedMessageException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void closeOrder(String spMchid, String subMchid, String outTradeNo) {
        com.wechat.pay.java.service.partnerpayments.jsapi.JsapiService service = new com.wechat.pay.java.service.partnerpayments.jsapi.JsapiService.Builder()
            .config(partnerConfig).build();

        CloseOrderRequest request = new CloseOrderRequest();
        request.setSpMchid(spMchid);
        request.setSubMchid(subMchid);
        request.setOutTradeNo(outTradeNo);

        try {
            service.closeOrder(request);
        } catch (HttpException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException("发送HTTP请求失败");
        } catch (ServiceException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getErrorMessage());
        } catch (MalformedMessageException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Transaction queryOrderByOutTradeNo(String spMchid, String subMchid, String outTradeNo) {
        com.wechat.pay.java.service.partnerpayments.jsapi.JsapiService service = new com.wechat.pay.java.service.partnerpayments.jsapi.JsapiService.Builder()
            .config(partnerConfig).build();

        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        request.setSpMchid(spMchid);
        request.setSubMchid(subMchid);
        request.setOutTradeNo(outTradeNo);

        try {
            return service.queryOrderByOutTradeNo(request);
        } catch (HttpException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException("发送HTTP请求失败");
        } catch (ServiceException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getErrorMessage());
        } catch (MalformedMessageException e) {
            logger.error(request.toString(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

}
