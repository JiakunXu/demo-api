package com.example.demo.file.service;

import com.example.demo.aliyun.api.OssService;
import com.example.demo.file.api.FileService;
import com.example.demo.file.api.bo.File;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author JiakunXu
 */
@Service
public class FileServiceImpl implements FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private OssService          ossService;

    @Value("${aliyun.oss.bucketName}")
    private String              bucketName;

    @Value("${aliyun.oss.url}")
    private String              url;

    private String getKey(String name) {
        return DateTime.now().toString("yyyy/MM/dd/") + UUID.randomUUID() + StringUtils
            .split(StringUtils.substring(name, StringUtils.lastIndexOf(name, ".")), "?")[0];
    }

    @Override
    public File upload(String name, String contentType, InputStream content) {
        if (StringUtils.isAnyBlank(name, contentType) || content == null) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "参数信息不能为空");
        }

        File file = new File();
        file.setName(name);
        file.setContentType(contentType);
        file.setUrl(
            url + "/" + ossService.putObject(bucketName, getKey(name), content, contentType));

        return file;
    }

}
