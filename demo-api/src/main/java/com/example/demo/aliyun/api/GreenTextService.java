package com.example.demo.aliyun.api;

import com.example.demo.aliyun.api.bo.green.Return;

/**
 * @author JiakunXu
 */
public interface GreenTextService {

    /**
     *
     * @param content
     * @return
     */
    Return scan(String[] content) throws Exception;

}
