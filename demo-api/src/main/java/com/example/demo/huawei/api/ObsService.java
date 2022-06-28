package com.example.demo.huawei.api;

import java.io.InputStream;

public interface ObsService {

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

}
