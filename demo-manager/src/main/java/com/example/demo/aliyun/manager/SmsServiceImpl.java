package com.example.demo.aliyun.manager;

import com.alibaba.fastjson2.JSON;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.Common;
import com.example.demo.aliyun.api.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class SmsServiceImpl implements SmsService {

    private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Value("${aliyun.accessKey.id}")
    private String              accessKeyId;

    @Value("${aliyun.accessKey.secret}")
    private String              accessKeySecret;

    @Override
    public String send(String signName, String templateCode, String templateParam,
                       String phoneNumbers) {
        Client client;

        try {
            client = new Client(new Config().setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret).setEndpoint("dysmsapi.aliyuncs.com"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        SendSmsRequest request = new SendSmsRequest().setSignName(signName)
            .setTemplateCode(templateCode).setTemplateParam(templateParam)
            .setPhoneNumbers(phoneNumbers);

        SendSmsResponse response;

        try {
            response = client.sendSms(request);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(request), e);

            TeaException err = e instanceof TeaException ? (TeaException) e
                : new TeaException(e.getMessage(), e);
            if (!Common.empty(err.code) && !Common.empty(err.message)) {
                throw new RuntimeException(err.message);
            } else {
                throw new RuntimeException(e);
            }
        }

        if (!"OK".equals(response.getBody().getCode())) {
            throw new RuntimeException(response.getBody().getMessage());
        }

        return response.getBody().getBizId();
    }

}
