package com.example.demo.file.service;

import com.example.demo.aliyun.api.OssService;
import com.example.demo.bytedance.api.ImageService;
import com.example.demo.bytedance.api.TokenService;
import com.example.demo.file.api.FileService;
import com.example.demo.file.api.bo.File;
import com.example.demo.bytedance.api.bo.text.Body;
import com.example.demo.bytedance.api.bo.text.Log;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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

        Body.Task image = new Body.Task(null, Base64.getEncoder().encodeToString(src));

        List<Body.Task> taskList = new ArrayList<>();
        taskList.add(image);

        Body body = new Body();
        body.setTargetList(targetList);
        body.setTaskList(taskList);

        Log log = null;

        try {
            log = imageService.detect(tokenService.getToken(appId, appSecret, "client_credential"),
                body);
        } catch (Exception e) {
            logger.error(body.toString(), e);
        }

        if (log != null) {
            List<Log.Data> dataList = log.getDataList();
            for (Log.Data data : dataList) {
                if (data.getCode() == 0) {
                    List<Log.Data.Predict> predictList = data.getPredictList();
                    for (Log.Data.Predict predict : predictList) {
                        if (predict.getProb() == 1) {
                            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR,
                                "【图片】包含违法违规内容");
                        }
                    }
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public File insertFile(String name, byte[] content, String contentType) throws IOException {
        if (StringUtils.isBlank(name) || content == null || StringUtils.isBlank(contentType)) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "参数信息不能为空");
        }

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
