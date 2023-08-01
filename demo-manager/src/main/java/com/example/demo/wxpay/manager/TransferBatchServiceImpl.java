package com.example.demo.wxpay.manager;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.util.IOUtils;
import com.example.demo.wxpay.api.TransferBatchService;
import com.example.demo.wxpay.api.bo.TransferBatch;
import com.example.demo.wxpay.api.bo.TransferDetail;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;

public class TransferBatchServiceImpl implements TransferBatchService {

    private static final Logger logger = LoggerFactory.getLogger(TransferBatchServiceImpl.class);

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
    public TransferBatch initiateBatchTransfer(TransferBatch transferBatch) {
        if (transferBatch == null) {
            throw new RuntimeException("转账信息不能为空.");
        }

        CloseableHttpClient httpClient = createDefault();

        HttpPost httpPost = new HttpPost(HTTPS_POST_URL);
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type", "application/json; charset=utf-8");
        httpPost
            .setEntity(new StringEntity(JSON.toJSONString(transferBatch), StandardCharsets.UTF_8));

        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                return JSON.parseObject(EntityUtils.toString(response.getEntity()),
                    TransferBatch.class);
            } else {
                JSONObject result = JSON.parseObject(EntityUtils.toString(response.getEntity()),
                    JSONObject.class);
                throw new RuntimeException(result.getString("message"));
            }
        } catch (IOException e) {
            logger.error(transferBatch.toString(), e);
        } finally {
            IOUtils.close(httpClient);
        }

        throw new RuntimeException("发起商家转账失败.");
    }

    @Override
    public TransferBatch getTransferBatchByNo(String batchId) {
        if (StringUtils.isBlank(batchId)) {
            throw new RuntimeException("微信单号不能为空.");
        }

        CloseableHttpClient httpClient = createDefault();

        HttpGet httpGet = new HttpGet(StringUtils.replace(HTTPS_GET0_URL, "{batch_id}", batchId));
        httpGet.addHeader("Accept", "application/json");

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            JSONObject result = JSON.parseObject(EntityUtils.toString(response.getEntity()),
                JSONObject.class);
            if (status >= 200 && status < 300) {
                return JSON.parseObject(result.getString("transfer_batch"), TransferBatch.class);
            } else {
                throw new RuntimeException(result.getString("message"));
            }
        } catch (IOException e) {
            logger.error(batchId, e);
        } finally {
            IOUtils.close(httpClient);
        }

        throw new RuntimeException("通过微信批次单号查询批次单失败.");
    }

    @Override
    public TransferBatch getTransferBatchByOutNo(String outBatchNo) {
        if (StringUtils.isBlank(outBatchNo)) {
            throw new RuntimeException("商家单号不能为空.");
        }

        CloseableHttpClient httpClient = createDefault();

        HttpGet httpGet = new HttpGet(
            StringUtils.replace(HTTPS_GET1_URL, "{out_batch_no}", outBatchNo));
        httpGet.addHeader("Accept", "application/json");

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            JSONObject result = JSON.parseObject(EntityUtils.toString(response.getEntity()),
                JSONObject.class);
            if (status >= 200 && status < 300) {
                return JSON.parseObject(result.getString("transfer_batch"), TransferBatch.class);
            } else {
                throw new RuntimeException(result.getString("message"));
            }
        } catch (IOException e) {
            logger.error(outBatchNo, e);
        } finally {
            IOUtils.close(httpClient);
        }

        throw new RuntimeException("通过商家批次单号查询批次单失败.");
    }

    @Override
    public TransferDetail getTransferDetailByNo(String batchId, String detailId) {
        if (StringUtils.isAnyBlank(batchId, detailId)) {
            throw new RuntimeException("微信单号不能为空.");
        }

        CloseableHttpClient httpClient = createDefault();

        HttpGet httpGet = new HttpGet(StringUtils.replace(
            StringUtils.replace(HTTPS_GET2_URL, "{batch_id}", batchId), "{detail_id}", detailId));
        httpGet.addHeader("Accept", "application/json");

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                return JSON.parseObject(EntityUtils.toString(response.getEntity()),
                    TransferDetail.class);
            } else {
                JSONObject result = JSON.parseObject(EntityUtils.toString(response.getEntity()),
                    JSONObject.class);
                throw new RuntimeException(result.getString("message"));
            }
        } catch (IOException e) {
            logger.error(batchId + "&" + detailId, e);
        } finally {
            IOUtils.close(httpClient);
        }

        throw new RuntimeException("通过微信明细单号查询明细单失败.");
    }

    @Override
    public TransferDetail getTransferDetailByOutNo(String outBatchNo, String outDetailNo) {
        if (StringUtils.isAnyBlank(outBatchNo, outDetailNo)) {
            throw new RuntimeException("商家单号不能为空.");
        }

        CloseableHttpClient httpClient = createDefault();

        HttpGet httpGet = new HttpGet(
            StringUtils.replace(StringUtils.replace(HTTPS_GET3_URL, "{out_batch_no}", outBatchNo),
                "{out_detail_no}", outDetailNo));
        httpGet.addHeader("Accept", "application/json");

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                return JSON.parseObject(EntityUtils.toString(response.getEntity()),
                    TransferDetail.class);
            } else {
                JSONObject result = JSON.parseObject(EntityUtils.toString(response.getEntity()),
                    JSONObject.class);
                throw new RuntimeException(result.getString("message"));
            }
        } catch (IOException e) {
            logger.error(outBatchNo + "&" + outDetailNo, e);
        } finally {
            IOUtils.close(httpClient);
        }

        throw new RuntimeException("通过商家明细单号查询明细单失败.");
    }

}
