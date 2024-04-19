package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.weixin.api.SecurityCheckService;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.framework.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Service
public class SecurityCheckServiceImpl implements SecurityCheckService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityCheckServiceImpl.class);

    @Override
    public BaseResult msgSecCheck(String accessToken, String content) throws RuntimeException {
        BaseResult result;

        try {
            result = JSON.parseObject(
                HttpUtil.post(MessageFormat.format(HTTPS_POST_URL, accessToken), content),
                BaseResult.class);
        } catch (Exception e) {
            logger.error(accessToken, e);
            throw new RuntimeException(e);
        }

        if (result == null) {
            throw new RuntimeException("result is null.");
        }

        if (result.getErrCode() != 0) {
            throw new RuntimeException(result.getErrMsg());
        }

        return result;
    }

}
