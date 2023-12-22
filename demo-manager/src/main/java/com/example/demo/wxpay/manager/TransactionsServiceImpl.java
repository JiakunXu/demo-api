package com.example.demo.wxpay.manager;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.util.IOUtils;
import com.example.demo.wxpay.api.TransactionsService;
import com.example.demo.wxpay.api.bo.Transaction;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionsServiceImpl.class);

    @Value("${wxpay.apiV3Key}")
    private String              apiV3Key;

    @Value("${wxpay.merchant.id}")
    private String              merchantId;

    @Value("${wxpay.merchant.serialNumber}")
    private String              serialNumber;

    @Value("${wxpay.privateKey.path}")
    private String              privateKeyPath;

    private CloseableHttpClient createDefault() {
        PrivateKey privateKey;

        try {
            privateKey = PemUtil.loadPrivateKey(new FileInputStream(privateKeyPath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("证书不存在.");
        }

        Verifier verifier;

        try {
            CertificatesManager certificatesManager = CertificatesManager.getInstance();
            certificatesManager.putMerchant(merchantId,
                new WechatPay2Credentials(merchantId,
                    new PrivateKeySigner(serialNumber, privateKey)),
                apiV3Key.getBytes(StandardCharsets.UTF_8));
            verifier = certificatesManager.getVerifier(merchantId);
        } catch (Exception e) {
            throw new RuntimeException("证书不存在.");
        }

        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
            .withMerchant(merchantId, serialNumber, privateKey)
            .withValidator(new WechatPay2Validator(verifier));

        return builder.build();
    }

    @Override
    public String getPrepayId(Transaction transaction) throws RuntimeException {
        if (transaction == null) {
            throw new RuntimeException("订单信息不能为空.");
        }

        CloseableHttpClient httpClient = createDefault();

        HttpPost httpPost = new HttpPost(HTTPS_JSAPI_URL);
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");
        httpPost
            .setEntity(new StringEntity(JSON.toJSONString(transaction), StandardCharsets.UTF_8));

        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            JSONObject result = JSON.parseObject(EntityUtils.toString(response.getEntity()));
            if (status >= 200 && status < 300) {
                return result.getString("prepay_id");
            } else {
                throw new RuntimeException(result.getString("message"));
            }
        } catch (IOException e) {
            logger.error(transaction.toString(), e);
        } finally {
            IOUtils.close(httpClient);
        }

        throw new RuntimeException("JSAPI下单失败.");
    }

    @Override
    public String getH5Url(Transaction transaction) throws RuntimeException {
        if (transaction == null) {
            throw new RuntimeException("订单信息不能为空.");
        }

        CloseableHttpClient httpClient = createDefault();

        HttpPost httpPost = new HttpPost(HTTPS_H5_URL);
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");
        httpPost
            .setEntity(new StringEntity(JSON.toJSONString(transaction), StandardCharsets.UTF_8));

        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            JSONObject result = JSON.parseObject(EntityUtils.toString(response.getEntity()));
            if (status >= 200 && status < 300) {
                return result.getString("h5_url");
            } else {
                throw new RuntimeException(result.getString("message"));
            }
        } catch (IOException e) {
            logger.error(transaction.toString(), e);
        } finally {
            IOUtils.close(httpClient);
        }

        throw new RuntimeException("H5下单失败.");
    }

    @Override
    public String getCodeUrl(Transaction transaction) throws RuntimeException {
        if (transaction == null) {
            throw new RuntimeException("订单信息不能为空.");
        }

        CloseableHttpClient httpClient = createDefault();

        HttpPost httpPost = new HttpPost(HTTPS_NATIVE_URL);
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");
        httpPost
            .setEntity(new StringEntity(JSON.toJSONString(transaction), StandardCharsets.UTF_8));

        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            JSONObject result = JSON.parseObject(EntityUtils.toString(response.getEntity()));
            if (status >= 200 && status < 300) {
                return result.getString("code_url");
            } else {
                throw new RuntimeException(result.getString("message"));
            }
        } catch (IOException e) {
            logger.error(transaction.toString(), e);
        } finally {
            IOUtils.close(httpClient);
        }

        throw new RuntimeException("Native下单失败.");
    }

    @Override
    public String get(String spMchid, String subMchid, String outTradeNo) throws RuntimeException {
        if (StringUtils.isAnyBlank(spMchid, subMchid, outTradeNo)) {
            throw new RuntimeException("订单信息不能为空.");
        }

        CloseableHttpClient httpClient = createDefault();

        HttpGet httpGet = new HttpGet(
            HTTPS_TRADE_URL + "/" + outTradeNo + "?sp_mchid=" + spMchid + "&sub_mchid=" + subMchid);
        httpGet.addHeader("Accept", "application/json");

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                return EntityUtils.toString(response.getEntity());
            } else {
                JSONObject result = JSON.parseObject(EntityUtils.toString(response.getEntity()));
                throw new RuntimeException(result.getString("message"));
            }
        } catch (IOException e) {
            logger.error(spMchid + "&" + subMchid + "&" + outTradeNo, e);
        } finally {
            IOUtils.close(httpClient);
        }

        throw new RuntimeException("查询订单失败.");
    }

    @Override
    public void close(String spMchid, String subMchid, String outTradeNo) throws RuntimeException {
        if (StringUtils.isAnyBlank(spMchid, subMchid, outTradeNo)) {
            throw new RuntimeException("订单信息不能为空.");
        }

        JSONObject object = new JSONObject();
        object.put("sp_mchid", spMchid);
        object.put("sub_mchid", subMchid);

        CloseableHttpClient httpClient = createDefault();

        HttpPost httpPost = new HttpPost(HTTPS_TRADE_URL + "/" + outTradeNo + "/close");
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");
        httpPost.setEntity(new StringEntity(object.toJSONString(), StandardCharsets.UTF_8));

        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                return;
            } else {
                JSONObject result = JSON.parseObject(EntityUtils.toString(response.getEntity()));
                throw new RuntimeException(result.getString("message"));
            }
        } catch (IOException e) {
            logger.error(outTradeNo, e);
        } finally {
            IOUtils.close(httpClient);
        }

        throw new RuntimeException("关闭订单失败.");
    }

}
