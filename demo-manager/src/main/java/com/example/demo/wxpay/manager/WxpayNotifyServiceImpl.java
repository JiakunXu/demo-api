package com.example.demo.wxpay.manager;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.util.BeanUtil;
import com.example.demo.wxpay.api.TransactionsService;
import com.example.demo.wxpay.api.WxpayNotifyService;
import com.example.demo.wxpay.api.bo.WxpayNotify;
import com.example.demo.wxpay.dao.dataobject.WxpayNotifyDO;
import com.example.demo.wxpay.dao.mapper.WxpayNotifyMapper;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import com.wechat.pay.contrib.apache.httpclient.exception.ParseException;
import com.wechat.pay.contrib.apache.httpclient.exception.ValidationException;
import com.wechat.pay.contrib.apache.httpclient.notification.Notification;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationHandler;
import com.wechat.pay.contrib.apache.httpclient.notification.NotificationRequest;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.UUID;

@Service
public class WxpayNotifyServiceImpl implements WxpayNotifyService {

    private static final Logger logger = LoggerFactory.getLogger(WxpayNotifyServiceImpl.class);

    @Autowired
    private TransactionsService transactionsService;

    @Autowired
    private WxpayNotifyMapper   wxpayNotifyMapper;

    @Value("${wxpay.apiV3Key}")
    private String              apiV3Key;

    @Value("${wxpay.merchant.id}")
    private String              merchantId;

    @Value("${wxpay.merchant.serialNumber}")
    private String              serialNumber;

    @Value("${wxpay.privateKey.path}")
    private String              privateKeyPath;

    @Override
    public WxpayNotify getWxpayNotify(String serialNumber, String nonce, String timestamp,
                                      String signature, String body) {
        NotificationRequest request = new NotificationRequest.Builder()
            .withSerialNumber(serialNumber).withNonce(nonce).withTimestamp(timestamp)
            .withSignature(signature).withBody(body).build();

        PrivateKey privateKey;

        try {
            privateKey = PemUtil.loadPrivateKey(new FileInputStream(privateKeyPath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Verifier verifier;

        try {
            CertificatesManager certificatesManager = CertificatesManager.getInstance();
            certificatesManager.putMerchant(merchantId,
                new WechatPay2Credentials(merchantId,
                    new PrivateKeySigner(this.serialNumber, privateKey)),
                apiV3Key.getBytes(StandardCharsets.UTF_8));
            verifier = certificatesManager.getVerifier(merchantId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            NotificationHandler handler = new NotificationHandler(verifier,
                apiV3Key.getBytes(StandardCharsets.UTF_8));
            Notification notification = handler.parse(request);

            JSONObject decryptData = JSON.parseObject(notification.getDecryptData(),
                JSONObject.class);

            WxpayNotify wxpayNotify = new WxpayNotify();
            wxpayNotify.setId(notification.getId());
            wxpayNotify.setCreateTime(notification.getCreateTime());
            wxpayNotify.setEventType(notification.getEventType());
            wxpayNotify.setResourceType(notification.getResourceType());
            wxpayNotify.setSummary(notification.getSummary());
            wxpayNotify.setSpAppid(decryptData.getString("sp_appid"));
            wxpayNotify.setSpMchid(decryptData.getString("sp_mchid"));
            wxpayNotify.setSubAppid(decryptData.getString("sub_appid"));
            wxpayNotify.setSubMchid(decryptData.getString("sub_mchid"));
            wxpayNotify.setOutTradeNo(decryptData.getString("out_trade_no"));
            wxpayNotify.setTransactionId(decryptData.getString("transaction_id"));
            wxpayNotify.setTradeType(decryptData.getString("trade_type"));
            wxpayNotify.setTradeState(decryptData.getString("trade_state"));
            wxpayNotify.setTradeStateDesc(decryptData.getString("trade_state_desc"));
            wxpayNotify.setBankType(decryptData.getString("bank_type"));
            wxpayNotify.setAttach(decryptData.getString("attach"));
            wxpayNotify.setSuccessTime(decryptData.getString("success_time"));
            wxpayNotify.setPayer(decryptData.getString("payer"));
            wxpayNotify.setAmount(decryptData.getString("amount"));
            wxpayNotify.setSceneInfo(decryptData.getString("scene_info"));
            wxpayNotify.setPromotionDetail(decryptData.getString("promotion_detail"));

            return wxpayNotify;
        } catch (ValidationException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WxpayNotify getWxpayNotify(String subMchid, String outTradeNo) {
        JSONObject data = JSON.parseObject(
            transactionsService.get(merchantId, subMchid, outTradeNo), JSONObject.class);

        WxpayNotify wxpayNotify = new WxpayNotify();
        wxpayNotify.setId(UUID.randomUUID().toString());
        wxpayNotify.setSpAppid(data.getString("sp_appid"));
        wxpayNotify.setSpMchid(data.getString("sp_mchid"));
        wxpayNotify.setSubAppid(data.getString("sub_appid"));
        wxpayNotify.setSubMchid(data.getString("sub_mchid"));
        wxpayNotify.setOutTradeNo(data.getString("out_trade_no"));
        wxpayNotify.setTransactionId(data.getString("transaction_id"));
        wxpayNotify.setTradeType(data.getString("trade_type"));
        wxpayNotify.setTradeState(data.getString("trade_state"));
        wxpayNotify.setTradeStateDesc(data.getString("trade_state_desc"));
        wxpayNotify.setBankType(data.getString("bank_type"));
        wxpayNotify.setAttach(data.getString("attach"));
        wxpayNotify.setSuccessTime(data.getString("success_time"));
        wxpayNotify.setPayer(data.getString("payer"));
        wxpayNotify.setAmount(data.getString("amount"));
        wxpayNotify.setSceneInfo(data.getString("scene_info"));
        wxpayNotify.setPromotionDetail(data.getString("promotion_detail"));

        return wxpayNotify;
    }

    @Override
    public WxpayNotify insertWxpayNotify(WxpayNotify wxpayNotify) {
        if (wxpayNotify == null) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "参数信息不能为空");
        }

        WxpayNotifyDO wxpayNotifyDO = BeanUtil.copy(wxpayNotify, WxpayNotifyDO.class);

        try {
            wxpayNotifyMapper.insert(wxpayNotifyDO);
        } catch (Exception e) {
            logger.error(wxpayNotifyDO.toString(), e);
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "信息创建失败，请稍后再试");
        }

        return wxpayNotify;
    }

}
