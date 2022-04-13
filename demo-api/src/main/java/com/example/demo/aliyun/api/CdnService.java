package com.example.demo.aliyun.api;

/**
 * @author JiakunXu
 */
public interface CdnService {

    /**
     *
     * @param requestURI
     * @return
     */
    String getAuthKey(String requestURI);

    /**
     *
     * @param type
     * @param requestURI
     * @return
     */
    String getAuthKey(String type, String requestURI);

}
