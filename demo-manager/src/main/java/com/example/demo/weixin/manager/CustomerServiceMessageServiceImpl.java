package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.CustomerServiceMessageService;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.message.Typing;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class CustomerServiceMessageServiceImpl implements CustomerServiceMessageService {

    private static final Logger logger = LoggerFactory
        .getLogger(CustomerServiceMessageServiceImpl.class);

    @Override
    public BaseResult setTyping(String accessToken, String toUser,
                                Typing typing) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(toUser)) {
            throw new RuntimeException("touser cannot be null.");
        }

        if (typing == null) {
            throw new RuntimeException("typing cannot be null.");
        }

        typing.setToUser(toUser);

        BaseResult result = null;

        try {
            result = JSON.parseObject(
                HttpUtil.post(CustomerServiceMessageService.HTTPS_TYPING_URL + accessToken,
                    JSON.toJSONString(typing)),
                BaseResult.class);
        } catch (Exception e) {
            logger.error(typing.toString(), e);

            throw new RuntimeException("HttpUtil error.", e);
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
