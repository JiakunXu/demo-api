package com.example.demo.aliyun.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.example.demo.aliyun.api.OssService;
import com.example.demo.framework.exception.ServiceException;

/**
 * @author JiakunXu
 */
@Service
public class OssServiceImpl implements OssService {

    private static final Logger logger = LoggerFactory.getLogger(OssServiceImpl.class);

    @Value("${aliyun.oss.endpoint}")
    private String              endpoint;

    @Value("${aliyun.accessKey.id}")
    private String              accessKeyId;

    @Value("${aliyun.accessKey.secret}")
    private String              secretAccessKey;

    @Override
    public String putObject(String bucketName, String key, byte[] content, String contentType) {
        return putObject(bucketName, key, new ByteArrayInputStream(content), contentType);
    }

    @Override
    public String putObject(String bucketName, String key, InputStream content,
                            String contentType) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, secretAccessKey);

        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, content);

            // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            putObjectRequest.setMetadata(metadata);

            // 上传文件。
            ossClient.putObject(putObjectRequest);
        } catch (OSSException oe) {
            logger.error("Caught an OSSException, which means your request made it to OSS, "
                         + "but was rejected with an error response for some reason.");
            logger.error("Error Message: " + oe.getErrorMessage());
            logger.error("Error Code: " + oe.getErrorCode());
            logger.error("Request ID: " + oe.getRequestId());
            logger.error("Host ID: " + oe.getHostId());

            throw new ServiceException(oe.getMessage());
        } catch (ClientException ce) {
            logger.error("Caught an ClientException, which means the client encountered "
                         + "a serious internal problem while trying to communicate with OSS, "
                         + "such as not being able to access the network.");
            logger.error("Error Message: " + ce.getMessage());

            throw new ServiceException(ce.getMessage());
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }

        return key;
    }
}
