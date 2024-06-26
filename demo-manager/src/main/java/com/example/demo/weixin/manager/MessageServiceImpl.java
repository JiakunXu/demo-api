package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.MessageCryptService;
import com.example.demo.weixin.api.MessageService;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.message.Result;
import com.example.demo.weixin.api.bo.message.Subscribe;
import com.example.demo.weixin.api.bo.message.Template;
import com.example.demo.weixin.api.bo.message.Uniform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private MessageCryptService messageCryptService;

    @Value("${weixin.app.id}")
    private String              appId;

    @Value("${weixin.token}")
    private String              token;

    @Value("${weixin.encodingAesKey}")
    private String              encodingAesKey;

    @Override
    public String verify(String signature, String timestamp, String nonce,
                         String echoStr) throws RuntimeException {
        return messageCryptService.verify(token, encodingAesKey, appId, signature, timestamp, nonce,
            echoStr);
    }

    @Override
    public String notify(String signature, String timestamp, String nonce,
                         String data) throws RuntimeException {
        return messageCryptService.decrypt(token, encodingAesKey, appId, signature, timestamp,
            nonce, data);
    }

    @Override
    public Result send(String accessToken, String toUser,
                       Template template) throws RuntimeException {
        template.setToUser(toUser);

        Result result;

        try {
            result = JSON
                .parseObject(HttpUtil.post(MessageFormat.format(HTTPS_TEMPLATE_URL, accessToken),
                    JSON.toJSONString(template)), Result.class);
        } catch (Exception e) {
            logger.error(template.toString(), e);
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

    @Override
    public BaseResult send(String accessToken, String toUser,
                           Subscribe subscribe) throws RuntimeException {
        subscribe.setToUser(toUser);

        BaseResult result;

        try {
            result = JSON
                .parseObject(HttpUtil.post(MessageFormat.format(HTTPS_SUBSCRIBE_URL, accessToken),
                    JSON.toJSONString(subscribe)), BaseResult.class);
        } catch (Exception e) {
            logger.error(subscribe.toString(), e);
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

    @Override
    public BaseResult send(String accessToken, String toUser,
                           Uniform uniform) throws RuntimeException {
        uniform.setToUser(toUser);

        BaseResult result;

        try {
            result = JSON
                .parseObject(HttpUtil.post(MessageFormat.format(HTTPS_UNIFORM_URL, accessToken),
                    JSON.toJSONString(uniform)), BaseResult.class);
        } catch (Exception e) {
            logger.error(uniform.toString(), e);
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
