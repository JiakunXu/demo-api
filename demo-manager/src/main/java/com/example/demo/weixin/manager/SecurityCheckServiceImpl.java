package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.weixin.api.SecurityCheckService;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.framework.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Slf4j
@Service
public class SecurityCheckServiceImpl implements SecurityCheckService {

    @Override
    public BaseResult msgSecCheck(String accessToken, String content) throws RuntimeException {
        BaseResult result;

        try {
            result = JSON.parseObject(
                HttpUtil.post(MessageFormat.format(HTTPS_POST_URL, accessToken), content),
                BaseResult.class);
        } catch (Exception e) {
            log.error("{}", accessToken, e);
            throw new RuntimeException(e.getMessage(), e);
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
