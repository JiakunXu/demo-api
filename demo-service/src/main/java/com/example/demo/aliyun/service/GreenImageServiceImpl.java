package com.example.demo.aliyun.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.green.model.v20180509.ImageSyncScanRequest;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.example.demo.aliyun.api.GreenImageService;
import com.example.demo.aliyun.api.ao.green.Return;
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
public class GreenImageServiceImpl implements GreenImageService {

    private static final Logger logger = LoggerFactory.getLogger(GreenImageServiceImpl.class);

    @Value("${aliyun.green.region.id}")
    private String              regionId;

    @Value("${aliyun.accessKey.id}")
    private String              accessKeyId;

    @Value("${aliyun.accessKey.secret}")
    private String              secretAccessKey;

    @Override
    public Return scan(String[] url) {
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secretAccessKey);
        IAcsClient client = new DefaultAcsClient(profile);

        ImageSyncScanRequest imageSyncScanRequest = new ImageSyncScanRequest();
        // 指定api返回格式
        imageSyncScanRequest.setSysAcceptFormat(FormatType.JSON);
        imageSyncScanRequest.setHttpContentType(FormatType.JSON);
        // 指定请求方法
        imageSyncScanRequest.setSysMethod(com.aliyuncs.http.MethodType.POST);
        imageSyncScanRequest.setSysEncoding("UTF-8");
        imageSyncScanRequest.setSysRegionId(regionId);

        List<Map<String, Object>> tasks = new ArrayList<>();

        for (String u : url) {
            Map<String, Object> task = new LinkedHashMap<>();
            task.put("dataId", UUID.randomUUID().toString());
            task.put("url", u);
            tasks.add(task);
        }

        JSONObject data = new JSONObject();

        /**
         * 设置要检测的风险场景。计费依据此处传递的场景计算。
         * 一次请求中可以同时检测多张图片，每张图片可以同时检测多个风险场景，计费按照场景计算。
         * 例如：检测2张图片，场景传递porn和terrorism，计费会按照2张图片鉴黄，2张图片暴恐检测计算。
         * porn：表示鉴黄场景。
         */
        data.put("scenes", Arrays.asList("porn", "terrorism", "ad"));
        data.put("tasks", tasks);

        imageSyncScanRequest.setHttpContent(data.toJSONString().getBytes(), "UTF-8",
            FormatType.JSON);

        // 请务必设置超时时间
        imageSyncScanRequest.setSysConnectTimeout(3000);
        imageSyncScanRequest.setSysReadTimeout(10000);
        try {
            HttpResponse httpResponse = client.doAction(imageSyncScanRequest);
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
