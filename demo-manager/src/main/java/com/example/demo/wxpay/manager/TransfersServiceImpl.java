package com.example.demo.wxpay.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import com.example.demo.wxpay.api.TransfersService;
import com.example.demo.wxpay.api.bo.Transfers;
import com.example.demo.wxpay.api.bo.TransfersReturn;
import com.example.demo.framework.util.EncryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TransfersServiceImpl implements TransfersService {

    private static final Logger logger = LoggerFactory.getLogger(TransfersServiceImpl.class);

    @Override
    public Transfers transfers(Transfers transfers, String key,
                               String sslPath) throws RuntimeException {
        if (transfers == null) {
            throw new RuntimeException("transfers 企业付款单 不能为空.");
        }

        StringBuilder sign = new StringBuilder();

        sign.append("amount=").append(transfers.getAmount());
        sign.append("&check_name=").append(transfers.getCheckName());
        sign.append("&desc=").append(transfers.getDesc());
        if (StringUtils.isNotBlank(transfers.getDeviceInfo())) {
            sign.append("&device_info=").append(transfers.getDeviceInfo());
        }
        sign.append("&mch_appid=").append(transfers.getAppid());
        sign.append("&mchid=").append(transfers.getMchid());
        sign.append("&nonce_str=").append(transfers.getNonceStr());
        sign.append("&openid=").append(transfers.getOpenid());
        sign.append("&partner_trade_no=").append(transfers.getPartnerTradeNo());
        if (StringUtils.isNotBlank(transfers.getReUserName())) {
            sign.append("&re_user_name=").append(transfers.getReUserName());
        }
        sign.append("&spbill_create_ip=").append(transfers.getSpbillCreateIp());

        sign.append("&key=").append(key);

        try {
            transfers.setSign(EncryptUtil.encryptMD5(sign.toString()).toUpperCase());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<mch_appid>" + transfers.getAppid() + "</mch_appid>");
        sb.append("<mchid>" + transfers.getMchid() + "</mchid>");

        if (StringUtils.isNotBlank(transfers.getDeviceInfo())) {
            sb.append("<device_info>" + transfers.getDeviceInfo() + "</device_info>");
        }

        sb.append("<nonce_str>" + transfers.getNonceStr() + "</nonce_str>");
        sb.append("<partner_trade_no>" + transfers.getPartnerTradeNo() + "</partner_trade_no>");
        sb.append("<openid>" + transfers.getOpenid() + "</openid>");
        sb.append("<check_name>" + transfers.getCheckName() + "</check_name>");

        if (StringUtils.isNotBlank(transfers.getReUserName())) {
            sb.append("<re_user_name>" + transfers.getReUserName() + "</re_user_name>");
        }

        sb.append("<amount>" + transfers.getAmount() + "</amount>");
        sb.append("<desc>" + transfers.getDesc() + "</desc>");
        sb.append("<spbill_create_ip>" + transfers.getSpbillCreateIp() + "</spbill_create_ip>");
        sb.append("<sign>" + transfers.getSign() + "</sign>");
        sb.append("</xml>");

        String result = null;

        try {
            result = post(TransfersService.HTTPS_TRANSFERS_URL, sb.toString(), transfers.getMchid(),
                sslPath);
        } catch (Exception e) {
            logger.error(sb.toString(), e);
        }

        if (StringUtils.isEmpty(result)) {
            throw new RuntimeException("申请付款失败.");
        }

        TransfersReturn ret = XmlUtil.parse(result, new TransfersReturn());

        if (ret == null) {
            throw new RuntimeException("申请付款失败.");
        }

        // 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
        if ("FAIL".equals(ret.getReturnCode())) {
            throw new RuntimeException(ret.getReturnMsg());
        }

        if ("FAIL".equals(ret.getResultCode())) {
            throw new RuntimeException(ret.getErrCodeDes());
        }

        return ret.getTransfers();
    }

    /**
     *
     * @param url
     * @param str
     * @param mchId
     * @param sslPath
     * @return
     * @throws Exception
     */
    private String post(String url, String str, String mchId, String sslPath) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(sslPath));
        try {
            keyStore.load(instream, mchId.toCharArray());
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray())
            .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,
            new String[] { "TLSv1" }, null,
            SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        try {
            HttpEntity httpEntity = null;
            httpEntity = new StringEntity(str, "UTF-8");

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(httpEntity);

            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(entity.getContent()));

                    StringBuilder responseStr = new StringBuilder();
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        responseStr.append(text);
                    }

                    return responseStr.toString();
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }

        return null;
    }

}
