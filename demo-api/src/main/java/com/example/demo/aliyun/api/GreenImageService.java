package com.example.demo.aliyun.api;

import com.example.demo.aliyun.api.bo.green.Return;

/**
 * @author JiakunXu
 */
public interface GreenImageService {

    /**
     *
     * @param url
     * @return
     */
    Return scan(String[] url);

}
