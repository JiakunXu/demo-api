package com.example.demo.aliyun.manager;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alinlp.model.v20200629.GetPosChEcomRequest;
import com.aliyuncs.alinlp.model.v20200629.GetPosChEcomResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.example.demo.aliyun.api.PosChEcomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PosChEcomServiceImpl implements PosChEcomService {

    @Value("${aliyun.region.id}")
    private String regionId;

    @Value("${aliyun.accessKey.id}")
    private String accessKeyId;

    @Value("${aliyun.accessKey.secret}")
    private String secret;

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
            log.error("{}", request, e);
            throw new RuntimeException(e.getMessage(), e);
        } catch (ClientException e) {
            log.error("{}", request, e);
            throw new RuntimeException(e.getMessage(), e);
        }

        return response.getData();
    }

}
