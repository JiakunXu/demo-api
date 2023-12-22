package com.example.demo.wxpay.service;

import com.alibaba.fastjson2.JSONObject;
import com.example.demo.wxpay.api.TransactionsService;
import com.example.demo.wxpay.api.WxpayService;
import com.example.demo.wxpay.api.bo.Transaction;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.Signer;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.UUID;

@Service
public class WxpayServiceImpl implements WxpayService {

    @Autowired
    private TransactionsService transactionsService;

    @Value("${wxpay.merchant.id}")
    private String              spMchid;

    @Value("${wxpay.merchant.serialNumber}")
    private String              serialNumber;

    @Value("${wxpay.privateKey.path}")
    private String              privateKeyPath;

    @Value("${wxpay.notify.url}")
    private String              notifyUrl;

    @Override
    public String build(String spAppid, String openid, String subMchid, String description,
                        String outTradeNo, String timeExpire, String attach, int totalFee) {
        Transaction transaction = new Transaction();
        transaction.setSpAppid(spAppid);
        transaction.setSpMchid(spMchid);
        transaction.setSubMchid(subMchid);
        transaction.setDescription(description);
        transaction.setOutTradeNo(outTradeNo);
        transaction.setTimeExpire(timeExpire);
        transaction.setAttach(attach);
        transaction.setNotifyUrl(notifyUrl);
        transaction.setAmount(new Transaction.Amount(totalFee));
        transaction.setPayer(new Transaction.Payer(openid));

        String prepayId = transactionsService.getPrepayId(transaction);

        PrivateKey privateKey;

        try {
            privateKey = PemUtil.loadPrivateKey(new FileInputStream(privateKeyPath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("证书不存在.");
        }

        Signer signer = new PrivateKeySigner(serialNumber, privateKey);

        String timeStamp = Long.toString(System.currentTimeMillis() / 1000);
        String nonceStr = UUID.randomUUID().toString().replace("-", "");

        String message = buildMessage(spAppid, timeStamp, nonceStr, "prepay_id=" + prepayId);
        Signer.SignatureResult signature = signer.sign(message.getBytes(StandardCharsets.UTF_8));

        JSONObject object = new JSONObject();
        object.put("appId", spAppid);
        object.put("timeStamp", timeStamp);
        object.put("nonceStr", nonceStr);
        object.put("package", "prepay_id=" + prepayId);
        object.put("signType", "RSA");
        object.put("paySign", signature.getSign());

        return object.toJSONString();
    }

    private String buildMessage(String appId, String timeStamp, String nonceStr,
                                String packageValue) {
        return appId + "\n" + timeStamp + "\n" + nonceStr + "\n" + packageValue + "\n";
    }

    @Override
    public String build(String spAppid, String subMchid, String description, String outTradeNo,
                        String timeExpire, String attach, int totalFee, String ip) {
        Transaction transaction = new Transaction();
        transaction.setSpAppid(spAppid);
        transaction.setSpMchid(spMchid);
        transaction.setSubMchid(subMchid);
        transaction.setDescription(description);
        transaction.setOutTradeNo(outTradeNo);
        transaction.setTimeExpire(timeExpire);
        transaction.setAttach(attach);
        transaction.setNotifyUrl(notifyUrl);
        transaction.setAmount(new Transaction.Amount(totalFee));

        Transaction.SceneInfo sceneInfo = new Transaction.SceneInfo();
        sceneInfo.setPayerClientIp(ip);
        sceneInfo.setH5Info(new Transaction.SceneInfo.H5Info("Wap"));
        transaction.setSceneInfo(sceneInfo);

        return transactionsService.getH5Url(transaction);
    }

    @Override
    public String build(String spAppid, String subMchid, String description, String outTradeNo,
                        String timeExpire, String attach, int totalFee) {
        Transaction transaction = new Transaction();
        transaction.setSpAppid(spAppid);
        transaction.setSpMchid(spMchid);
        transaction.setSubMchid(subMchid);
        transaction.setDescription(description);
        transaction.setOutTradeNo(outTradeNo);
        transaction.setTimeExpire(timeExpire);
        transaction.setAttach(attach);
        transaction.setNotifyUrl(notifyUrl);
        transaction.setAmount(new Transaction.Amount(totalFee));

        return transactionsService.getCodeUrl(transaction);
    }

    @Override
    public void close(String subMchid, String outTradeNo) {
        transactionsService.close(spMchid, subMchid, outTradeNo);
    }

}
