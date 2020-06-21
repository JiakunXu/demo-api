package com.example.demo.api.aliyun;

import com.example.demo.api.aliyun.ao.green.Return;

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
