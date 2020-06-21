package com.example.demo.aliyun.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.green.model.v20180509.TextScanRequest;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.example.demo.api.aliyun.GreenTextService;
import com.example.demo.api.aliyun.ao.green.Return;
import com.example.demo.framework.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author JiakunXu
 */
@Service
public class GreenTextServiceImpl implements GreenTextService {

    private static final Logger logger = LoggerFactory.getLogger(GreenTextServiceImpl.class);

    @Value("${aliyun.green.region.id}")
    private String              regionId;

    @Value("${aliyun.accessKey.id}")
    private String              accessKeyId;

    @Value("${aliyun.accessKey.secret}")
    private String              secretAccessKey;

    @Override
    public Return scan(String[] content) throws Exception {
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secretAccessKey);
        IAcsClient client = new DefaultAcsClient(profile);

        TextScanRequest textScanRequest = new TextScanRequest();
        // 指定api返回格式
        textScanRequest.setSysAcceptFormat(FormatType.JSON);
        textScanRequest.setHttpContentType(FormatType.JSON);
        // 指定请求方法
        textScanRequest.setSysMethod(com.aliyuncs.http.MethodType.POST);
        textScanRequest.setSysEncoding("UTF-8");
        textScanRequest.setSysRegionId(regionId);

        List<Map<String, Object>> tasks = new ArrayList<>();

        for (String c : content) {
            Map<String, Object> task = new LinkedHashMap<>();
            task.put("dataId", UUID.randomUUID().toString());
            task.put("content", c);
            tasks.add(task);
        }

        JSONObject data = new JSONObject();

        /**
         * 检测场景，文本垃圾检测传递：antispam
         **/
        data.put("scenes", Arrays.asList("antispam"));
        data.put("tasks", tasks);

        textScanRequest.setHttpContent(data.toJSONString().getBytes("UTF-8"), "UTF-8",
            FormatType.JSON);

        // 请务必设置超时时间
        textScanRequest.setSysConnectTimeout(3000);
        textScanRequest.setSysReadTimeout(6000);
        try {
            HttpResponse httpResponse = client.doAction(textScanRequest);
            if (httpResponse.isSuccess()) {
                return JSON.parseObject(httpResponse.getHttpContent(), Return.class);
            } else {
                throw new ServiceException(
                    "response not success. status:" + httpResponse.getStatus());
            }
        } catch (Exception e) {
            logger.error("scan", e);
            throw new ServiceException(e.getMessage());
        }
    }

}
