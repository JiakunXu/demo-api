package com.example.demo.aliyun.api;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * @author JiakunXu
 */
public interface OssService {

    /**
     *
     * @param bucketName
     * @param key
     * @param content
     * @param contentType
     * @return
     */
    String putObject(String bucketName, String key, byte[] content, String contentType);

    /**
     *
     * @param bucketName
     * @param key
     * @param content
     * @param contentType
     * @return
     */
    String putObject(String bucketName, String key, InputStream content, String contentType);

    /**
     * 
     * @param sourceBucketName
     * @param sourceKey
     * @param destinationBucketName
     * @param destinationKey
     * @param contentType
     * @return
     */
    String copyObject(String sourceBucketName, String sourceKey, String destinationBucketName,
                      String destinationKey, String contentType);

    URL generatePresignedUrl(String bucketName, String key);

    URL generatePresignedUrl(String bucketName, String key, Date expiration);

    URL generatePresignedUrl(String bucketName, String key, Date expiration, String process);

}
