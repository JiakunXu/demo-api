package com.example.demo.file.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.api.aliyun.OssService;
import com.example.demo.api.bytedance.ImageService;
import com.example.demo.api.bytedance.TokenService;
import com.example.demo.api.bytedance.ao.text.*;
import com.example.demo.api.file.FileService;
import com.example.demo.api.file.bo.File;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * @author JiakunXu
 */
@Service
public class FileServiceImpl implements FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private TokenService        tokenService;

    @Autowired
    private ImageService        imageService;

    @Autowired
    private OssService          ossService;

    @Value("${bytedance.app.id}")
    private String              appId;

    @Value("${bytedance.app.secret}")
    private String              appSecret;

    @Value("${aliyun.oss.bucketName}")
    private String              bucketName;

    @Value("${aliyun.oss.url}")
    private String              url;

    private void validate(byte[] src) {
        List<String> targetList = new ArrayList<>();
        targetList.add("porn");
        targetList.add("politics");
        targetList.add("disgusting");

        Task image = new Task(null, Base64.getEncoder().encodeToString(src));

        List<Task> taskList = new ArrayList<>();
        taskList.add(image);

        Body body = new Body();
        body.setTargetList(targetList);
        body.setTaskList(taskList);

        Log log = null;

        try {
            log = imageService.detect(tokenService.getToken(appId, appSecret, "client_credential"),
                body);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(body), e);
        }

        if (log != null) {
            List<Data> dataList = log.getDataList();
            for (Data data : dataList) {
                if (data.getCode() == 0) {
                    List<Predict> predictList = data.getPredictList();
                    for (Predict predict : predictList) {
                        if (predict.getProb() == 1) {
                            throw new ServiceException(Constants.BUSINESS_FAILED, "【图片】包含违法违规内容");
                        }
                    }
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public File insertFile(String name, InputStream input, String contentType) throws IOException {
        if (StringUtils.isBlank(name) || input == null || StringUtils.isBlank(contentType)) {
            throw new ServiceException(Constants.MISSING_PARAMETER, "参数信息不能为空");
        }

        byte[] content = IOUtils.toByteArray(input);

        validate(content);

        String key = DateTime.now().toString("yyyy/MM/dd/") + UUID.randomUUID().toString()
                     + StringUtils.split(
                         StringUtils.substring(name, StringUtils.lastIndexOf(name, ".")), "?")[0];

        File file = new File();
        file.setContentType(contentType);
        file.setUrl(url + "/" + key);

        ossService.putObject(bucketName, key, content, contentType);

        return file;
    }

}
