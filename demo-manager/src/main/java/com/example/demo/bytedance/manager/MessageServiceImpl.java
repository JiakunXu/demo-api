package com.example.demo.bytedance.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.bytedance.api.MessageService;
import com.example.demo.bytedance.api.TokenService;
import com.example.demo.bytedance.api.bo.message.Result;
import com.example.demo.bytedance.api.bo.message.Message;
import com.example.demo.framework.util.EncryptUtil;
import com.example.demo.framework.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JiakunXu
 */
@Service("com.example.demo.bytedance.manager.messageService")
public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private TokenService        tokenService;

    @Value("${bytedance.token}")
    private String              token;

    @Value("${bytedance.app.id}")
    private String              appId;

    @Value("${bytedance.app.secret}")
    private String              appSecret;

    private void validate(String signature, String timestamp, String nonce) {
        if (StringUtils.isBlank(signature)) {
            throw new RuntimeException("signature is null.");
        }

        if (StringUtils.isBlank(timestamp)) {
            throw new RuntimeException("timestamp is null.");
        }

        if (StringUtils.isBlank(nonce)) {
            throw new RuntimeException("nonce is null.");
        }

        String[] array = new String[] { token, timestamp, nonce };

        Arrays.sort(array);

        try {
            if (!signature.equals(EncryptUtil.encryptSha(array[0] + array[1] + array[2]))) {
                throw new RuntimeException("签名验证错误");
            }
        } catch (IOException e) {
            throw new RuntimeException("sha加密生成签名失败");
        }
    }

    private void validate(String signature, String timestamp, String nonce, String data) {
        if (StringUtils.isBlank(signature)) {
            throw new RuntimeException("signature is null.");
        }

        if (StringUtils.isBlank(timestamp)) {
            throw new RuntimeException("timestamp is null.");
        }

        if (StringUtils.isBlank(nonce)) {
            throw new RuntimeException("nonce is null.");
        }

        if (StringUtils.isBlank(data)) {
            throw new RuntimeException("data is null.");
        }

        String[] array = new String[] { token, timestamp, nonce, data };

        Arrays.sort(array);

        try {
            if (!signature
                .equals(EncryptUtil.encryptSha(array[0] + array[1] + array[2] + array[3]))) {
                throw new RuntimeException("签名验证错误");
            }
        } catch (IOException e) {
            throw new RuntimeException("sha加密生成签名失败");
        }
    }

    @Override
    public String verify(String signature, String timestamp, String nonce,
                         String echostr) throws RuntimeException {
        validate(signature, timestamp, nonce);

        return echostr;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Message notify(String signature, String timestamp, String nonce,
                          String data) throws RuntimeException {
        validate(signature, timestamp, nonce, data);

        Message message = JSON.parseObject(data, Message.class);

        try {
            send(tokenService.getToken(appId, appSecret, "client_credential"),
                message.getFromUserName(), "感谢您的留言。");
        } catch (RuntimeException e) {
            logger.error("send", e);
        }

        return message;
    }

    @Override
    public Result send(String accessToken, String openId, String content) throws RuntimeException {
        Map<String, String> map = new HashMap<>(3);
        map.put("open_id", openId);
        map.put("msg_type", "text");
        map.put("content", content);

        Result result;

        try {
            result = JSON.parseObject(
                HttpUtil.post(MessageService.HTTPS_POST_URL + accessToken, JSON.toJSONString(map)),
                Result.class);
        } catch (Exception e) {
            logger.error(JSON.toJSONString(map), e);
            throw new RuntimeException(e.getMessage(), e);
        }

        if (result == null) {
            throw new RuntimeException("send_result is null.");
        }

        if (result.getErrNo() != 0) {
            throw new RuntimeException(result.getMsg());
        }

        return result;
    }

}
