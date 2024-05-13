package com.example.demo.aliyun.manager;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alinlp.model.v20200629.GetPosChEcomRequest;
import com.aliyuncs.alinlp.model.v20200629.GetPosChEcomResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.example.demo.aliyun.api.PosChEcomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PosChEcomServiceImpl implements PosChEcomService {

    private static final Logger logger = LoggerFactory.getLogger(PosChEcomServiceImpl.class);

    @Value("${aliyun.region.id}")
    private String              regionId;

    @Value("${aliyun.accessKey.id}")
    private String              accessKeyId;

    @Value("${aliyun.accessKey.secret}")
    private String              secret;

    @Override
    public String getPosChEcom(String text) {
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        GetPosChEcomRequest request = new GetPosChEcomRequest();
        request.setSysEndpoint("alinlp.cn-hangzhou.aliyuncs.com");
        request.setServiceCode("alinlp");
        request.setText(text);
        request.setTokenizerId("GENERAL_CHN");

        GetPosChEcomResponse response;

        try {
            response = client.getAcsResponse(request);
        } catch (ServerException e) {
            logger.error("ServerException", e);
            throw new RuntimeException(e);
        } catch (ClientException e) {
            logger.error("ClientException", e);
            throw new RuntimeException(e);
        }

        return response.getData();
    }

}
