package com.example.demo.huawei.manager;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.ObjectMetadata;
import com.obs.services.model.PutObjectRequest;

import com.example.demo.framework.exception.ServiceException;
import com.example.demo.huawei.api.ObsService;

@Service
public class ObsServiceImpl implements ObsService {

    private static final Logger logger = LoggerFactory.getLogger(ObsServiceImpl.class);

    @Value("${huawei.accessKey.id}")
    private String              accessKey;

    @Value("${huawei.accessKey.secret}")
    private String              secretKey;

    @Value("${huawei.obs.endpoint}")
    private String              endPoint;

    @Override
    public String putObject(String bucketName, String key, byte[] content, String contentType) {
        return putObject(bucketName, key, new ByteArrayInputStream(content), contentType);
    }

    @Override
    public String putObject(String bucketName, String key, InputStream content,
                            String contentType) {
        ObsClient obsClient = new ObsClient(accessKey, secretKey, endPoint);

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, content);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(contentType);
            putObjectRequest.setMetadata(metadata);

            obsClient.putObject(putObjectRequest);
        } catch (ObsException oe) {
            logger.error("Response Code: " + oe.getResponseCode());
            logger.error("Error Message: " + oe.getErrorMessage());
            logger.error("Error Code: " + oe.getErrorCode());
            logger.error("Request ID: " + oe.getErrorRequestId());
            logger.error("Host ID: " + oe.getErrorHostId());

            throw new RuntimeException(oe.getErrorMessage(), oe);
        } finally {
            try {
                obsClient.close();
            } catch (IOException e) {
                logger.error("close", e);
            }
        }

        return key;
    }

}
