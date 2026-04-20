package com.example.demo.bytedance.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.bytedance.api.ImageService;
import com.example.demo.bytedance.api.bo.text.Body;
import com.example.demo.bytedance.api.bo.text.Log;
import com.example.demo.framework.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JiakunXu
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public Log detect(String accessToken, Body body) throws RuntimeException {
        Log log;

        try {
            Map<String, String> header = new HashMap<>(1);
            header.put("X-Token", accessToken);

            log = JSON.parseObject(
                HttpUtil.post(ImageService.HTTPS_POST_URL, JSON.toJSONString(body), header),
                Log.class);
        } catch (Exception e) {
            ImageServiceImpl.log.error("{}", body, e);
            throw new RuntimeException(e.getMessage(), e);
        }

        if (log == null) {
            throw new RuntimeException("log is null.");
        }

        if (StringUtils.isNotBlank(log.getCode())) {
            throw new RuntimeException(log.getMessage());
        }

        return log;
    }

}
