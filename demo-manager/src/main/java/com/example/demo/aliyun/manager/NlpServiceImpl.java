package com.example.demo.aliyun.manager;

import com.alibaba.fastjson2.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.example.demo.aliyun.api.NlpService;
import com.example.demo.aliyun.api.bo.nlp.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NlpServiceImpl implements NlpService {

    private static final Logger logger = LoggerFactory.getLogger(NlpServiceImpl.class);

    @Value("${aliyun.sms.region.id}")
    private String              regionId;

    @Value("${aliyun.accessKey.id}")
    private String              accessKeyId;

    @Value("${aliyun.accessKey.secret}")
    private String              secret;

    @Override
    public Data getWsChGeneral(String text) {
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysDomain("alinlp.cn-hangzhou.aliyuncs.com");
        request.setSysVersion("2020-06-29");
        request.setSysAction("GetWsChGeneral");
        request.putQueryParameter("ServiceCode", "alinlp");
        request.putQueryParameter("Text", text);
        request.putQueryParameter("TokenizerId", "GENERAL_CHN");

        Data data = null;

        try {
            CommonResponse response = client.getCommonResponse(request);
            data = JSON.parseObject(response.getData(), Data.class);
        } catch (ServerException e) {
            logger.error("ServerException", e);
            throw new RuntimeException(e.getMessage());
        } catch (ClientException e) {
            logger.error("ClientException", e);
            throw new RuntimeException(e.getMessage());
        }

        if (data == null) {
            throw new RuntimeException("data is null.");
        }

        String requestId = data.getRequestId();

        data = JSON.parseObject(data.getData(), Data.class);

        data.setRequestId(requestId);

        return data;
    }

}
