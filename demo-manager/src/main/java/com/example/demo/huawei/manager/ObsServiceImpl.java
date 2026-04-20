package com.example.demo.huawei.manager;

import com.example.demo.huawei.api.ObsService;
import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.ObjectMetadata;
import com.obs.services.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class ObsServiceImpl implements ObsService {

    @Value("${huawei.accessKey.id}")
    private String accessKey;

    @Value("${huawei.accessKey.secret}")
    private String secretKey;

    @Value("${huawei.obs.endpoint}")
    private String endPoint;

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
            log.error("Response Code: " + oe.getResponseCode());
            log.error("Error Message: " + oe.getErrorMessage());
            log.error("Error Code: " + oe.getErrorCode());
            log.error("Request ID: " + oe.getErrorRequestId());
            log.error("Host ID: " + oe.getErrorHostId());

            throw new RuntimeException(oe.getErrorMessage(), oe);
        } finally {
            try {
                obsClient.close();
            } catch (IOException e) {
                log.error("close", e);
            }
        }

        return key;
    }

}
