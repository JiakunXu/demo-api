package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.framework.constant.Constants;
import com.example.demo.framework.exception.ServiceException;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.MessageCryptService;
import com.example.demo.weixin.api.MessageService;
import com.example.demo.weixin.api.bo.BaseResult;
import com.example.demo.weixin.api.bo.message.Result;
import com.example.demo.weixin.api.bo.message.Subscribe;
import com.example.demo.weixin.api.bo.message.Template;
import com.example.demo.weixin.api.bo.message.Uniform;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
        if (StringUtils.isBlank(signature) || StringUtils.isBlank(timestamp)
            || StringUtils.isBlank(nonce)) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "参数信息不能为空");
        }

        return messageCryptService.verify(token, encodingAesKey, appId, signature, timestamp, nonce,
            echoStr);
    }

    @Override
    public String notify(String signature, String timestamp, String nonce,
                         String data) throws RuntimeException {
        if (StringUtils.isBlank(signature) || StringUtils.isBlank(timestamp)
            || StringUtils.isBlank(nonce)) {
            throw new ServiceException(Constants.INTERNAL_SERVER_ERROR, "参数信息不能为空");
        }

        String message = messageCryptService.decrypt(token, encodingAesKey, appId, signature,
            timestamp, nonce, data);

        System.out.println(message);

        message = StringUtils.replace(message, "ToUserName", "_ToUserName");
        message = StringUtils.replace(message, "FromUserName", "ToUserName");
        message = StringUtils.replace(message, "_ToUserName", "FromUserName");

        System.out.println(message);

        return messageCryptService.encrypt(token, encodingAesKey, appId, message, timestamp, nonce);
    }

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
