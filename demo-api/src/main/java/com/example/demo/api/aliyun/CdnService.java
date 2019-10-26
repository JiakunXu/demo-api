package com.example.demo.api.aliyun;

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
