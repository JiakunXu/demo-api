package com.example.demo.aliyun.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.aliyun.api.bo.sms.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.example.demo.aliyun.api.SmsService;

/**
 * @author JiakunXu
 */
@Service
public class SmsServiceImpl implements SmsService {

    private static final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Value("${aliyun.sms.region.id}")
    private String              regionId;

    @Value("${aliyun.accessKey.id}")
    private String              accessKeyId;

    @Value("${aliyun.accessKey.secret}")
    private String              secret;

    @Override
    public Data send(String signName, String templateCode, String templateParam,
                     String phoneNumbers) {
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", regionId);
        request.putQueryParameter("PhoneNumbers", phoneNumbers);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", templateParam);

        Data data = null;

        try {
            CommonResponse response = client.getCommonResponse(request);
            data = JSON.parseObject(response.getData(), Data.class);
        } catch (ServerException e) {
            logger.error("ServerException", e);
            throw new RuntimeException(e);
        } catch (ClientException e) {
            logger.error("ClientException", e);
            throw new RuntimeException(e);
        }

        if (data == null) {
            throw new RuntimeException("data is null.");
        }

        if (!"OK".equals(data.getCode())) {
            throw new RuntimeException(data.getMessage());
        }

        return data;
    }

}
