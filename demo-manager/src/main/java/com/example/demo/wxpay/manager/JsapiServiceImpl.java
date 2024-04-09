package com.example.demo.wxpay.manager;

import com.example.demo.wxpay.api.JsapiService;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.exception.HttpException;
import com.wechat.pay.java.core.exception.MalformedMessageException;
import com.wechat.pay.java.core.exception.ServiceException;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.*;
import com.wechat.pay.java.service.payments.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JsapiServiceImpl implements JsapiService {

    private static final Logger logger = LoggerFactory.getLogger(JsapiServiceImpl.class);

    @Autowired(required = false)
    private Config              merchantConfig;

    @Override
    public PrepayWithRequestPaymentResponse prepayWithRequestPayment(String appid, String mchid,
                                                                     String description,
                                                                     String outTradeNo,
                                                                     String timeExpire,
                                                                     String attach,
                                                                     String notifyUrl,
                                                                     Integer total, String openid) {
        JsapiServiceExtension service = new JsapiServiceExtension.Builder().config(merchantConfig)
            .build();

        PrepayRequest request = new PrepayRequest();

        request.setAppid(appid);
        request.setMchid(mchid);
        request.setDescription(description);
        request.setOutTradeNo(outTradeNo);
        request.setTimeExpire(timeExpire);
        request.setAttach(attach);
        request.setNotifyUrl(notifyUrl);

        Amount amount = new Amount();
        amount.setTotal(total);
        request.setAmount(amount);

        Payer payer = new Payer();
        payer.setOpenid(openid);
        request.setPayer(payer);

        SettleInfo settleInfo = new SettleInfo();
        settleInfo.setProfitSharing(Boolean.TRUE);
        request.setSettleInfo(settleInfo);

        try {
            return service.prepayWithRequestPayment(request);
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
    public void closeOrder(String mchid, String outTradeNo) {
        com.wechat.pay.java.service.payments.jsapi.JsapiService service = new com.wechat.pay.java.service.payments.jsapi.JsapiService.Builder()
            .config(merchantConfig).build();

        CloseOrderRequest request = new CloseOrderRequest();
        request.setMchid(mchid);
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
    public Transaction queryOrderByOutTradeNo(String mchid, String outTradeNo) {
        com.wechat.pay.java.service.payments.jsapi.JsapiService service = new com.wechat.pay.java.service.payments.jsapi.JsapiService.Builder()
            .config(merchantConfig).build();

        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        request.setMchid(mchid);
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
