package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.MessageService;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.message.Result;
import com.example.demo.weixin.api.bo.message.Subscribe;
import com.example.demo.weixin.api.bo.message.Template;
import com.example.demo.weixin.api.bo.message.Uniform;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Override
    public Result send(String accessToken, String toUser,
                       Template template) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(toUser)) {
            throw new RuntimeException("touser cannot be null.");
        }

        if (template == null || template.getData() == null) {
            throw new RuntimeException("template cannot be null.");
        }

        template.setToUser(toUser);

        Result result;

        try {
            result = JSON.parseObject(HttpUtil.post(MessageService.HTTPS_TEMPLATE_URL + accessToken,
                JSON.toJSONString(template)), Result.class);
        } catch (Exception e) {
            logger.error(template.toString(), e);

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

    @Override
    public BaseResult send(String accessToken, String toUser,
                           Subscribe subscribe) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(toUser)) {
            throw new RuntimeException("touser cannot be null.");
        }

        if (subscribe == null || subscribe.getData() == null) {
            throw new RuntimeException("subscribe cannot be null.");
        }

        subscribe.setToUser(toUser);

        BaseResult result;

        try {
            result = JSON
                .parseObject(HttpUtil.post(MessageService.HTTPS_SUBSCRIBE_URL + accessToken,
                    JSON.toJSONString(subscribe)), BaseResult.class);
        } catch (Exception e) {
            logger.error(subscribe.toString(), e);

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

    @Override
    public BaseResult send(String accessToken, String toUser,
                           Uniform uniform) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(toUser)) {
            throw new RuntimeException("touser cannot be null.");
        }

        if (uniform == null) {
            throw new RuntimeException("uniform cannot be null.");
        }

        uniform.setToUser(toUser);

        BaseResult result;

        try {
            result = JSON.parseObject(HttpUtil.post(MessageService.HTTPS_UNIFORM_URL + accessToken,
                JSON.toJSONString(uniform)), BaseResult.class);
        } catch (Exception e) {
            logger.error(uniform.toString(), e);

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
