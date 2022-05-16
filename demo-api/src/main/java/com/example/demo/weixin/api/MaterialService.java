package com.example.demo.weixin.api;

import com.example.demo.weixin.api.bo.material.Count;
import com.example.demo.weixin.api.bo.material.Material;
import com.example.demo.weixin.api.bo.material.Parameter;

/**
 * @author JiakunXu
 */
public interface MaterialService {

    String HTTPS_COUNT_URL = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=";

    String HTTPS_LIST_URL  = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=";

    /**
     * 
     * @param accessToken
     * @return
     * @throws RuntimeException
     */
    Count count(String accessToken) throws RuntimeException;

    /**
     * 
     * @param accessToken
     * @param parameter
     * @return
     * @throws RuntimeException
     */
    Material list(String accessToken, Parameter parameter) throws RuntimeException;

}
