package com.example.demo.bytedance.manager;

import com.alibaba.fastjson.JSON;
import com.example.demo.bytedance.api.TextService;
import com.example.demo.bytedance.api.bo.text.Body;
import com.example.demo.bytedance.api.bo.text.Log;
import com.example.demo.framework.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JiakunXu
 */
@Service
public class TextServiceImpl implements TextService {

    private static final Logger logger = LoggerFactory.getLogger(TextServiceImpl.class);

    @Override
    public Log detect(String accessToken, Body body) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token is null.");
        }

        if (body == null) {
            throw new RuntimeException("body is null.");
        }

        Log log = null;

        try {
            Map<String, String> header = new HashMap<>(1);
            header.put("X-Token", accessToken);

            log = JSON.parseObject(
                HttpUtil.post(TextService.HTTPS_DETECT_URL, JSON.toJSONString(body), header),
                Log.class);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(body), e);

            throw new RuntimeException("HttpUtil error.", e);
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
