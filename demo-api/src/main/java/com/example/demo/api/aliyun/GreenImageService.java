package com.example.demo.api.aliyun;

import com.example.demo.api.aliyun.ao.green.Return;

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
