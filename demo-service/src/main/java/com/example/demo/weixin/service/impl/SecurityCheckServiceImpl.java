package com.example.demo.weixin.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.demo.weixin.api.SecurityCheckService;
import com.example.demo.weixin.api.ao.BaseResult;
import com.example.demo.framework.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class SecurityCheckServiceImpl implements SecurityCheckService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityCheckServiceImpl.class);

    @Override
    public BaseResult msgSecCheck(String accessToken, String content) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(content)) {
            throw new RuntimeException("content cannot be null.");
        }

        BaseResult result = null;

        try {
            result = JSON.parseObject(HttpUtil.post(
                SecurityCheckService.HTTPS_MSG_URL.replace("$ACCESS_TOKEN$", accessToken.trim()),
                content), BaseResult.class);
        } catch (Exception e) {
            logger.error(accessToken, e);

            throw new RuntimeException("HttpUtil error.", e);
        }

        if (result == null) {
            throw new RuntimeException("result is null.");
        }

        String errCode = result.getErrCode();
        if (StringUtils.isNotBlank(errCode) && !"0".equals(errCode)) {
            throw new RuntimeException(result.getErrMsg());
        }

        return result;
    }

}
