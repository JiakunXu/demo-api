package com.example.demo.weixin.manager;

import com.alibaba.fastjson.JSON;
import com.example.demo.framework.util.HttpUtil;
import com.example.demo.weixin.api.MessageService;
import com.example.demo.weixin.api.bo.message.Message;
import com.example.demo.weixin.api.bo.message.Result;
import com.example.demo.weixin.api.bo.message.Template;
import com.example.demo.weixin.api.bo.message.Text;
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

    /**
     * 
     * @param accessToken
     * @param message
     * @return
     * @throws RuntimeException
     */
    private Result send(String accessToken, Message message) throws RuntimeException {
        Result result = null;

        try {
            result = JSON
                .parseObject(HttpUtil.post(MessageService.HTTPS_CUSTOM_URL + accessToken.trim(),
                    JSON.toJSONString(message)), Result.class);
        } catch (Exception e) {
            logger.error(message.toString(), e);

            throw new RuntimeException("HttpUtil error.", e);
        }

        if (result == null) {
            throw new RuntimeException("result is null.");
        }

        Integer errCode = result.getErrCode();
        if (errCode != null && errCode != 0) {
            throw new RuntimeException(result.getErrMsg());
        }

        return result;
    }

    @Override
    public Result send(String accessToken, String toUser, Text text) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(toUser)) {
            throw new RuntimeException("touser cannot be null.");
        }

        if (text == null || StringUtils.isBlank(text.getContent())) {
            throw new RuntimeException("content cannot be null.");
        }

        Message message = new Message();
        message.setToUser(toUser);
        message.setMsgType("text");
        message.setText(text);

        return send(accessToken, message);
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

        Result result = null;

        try {
            result = JSON
                .parseObject(HttpUtil.post(MessageService.HTTPS_TEMPLATE_URL + accessToken.trim(),
                    JSON.toJSONString(template)), Result.class);
        } catch (Exception e) {
            logger.error(template.toString(), e);

            throw new RuntimeException("HttpUtil error.", e);
        }

        if (result == null) {
            throw new RuntimeException("result is null.");
        }

        Integer errCode = result.getErrCode();
        if (errCode != null && errCode != 0) {
            throw new RuntimeException(result.getErrMsg());
        }

        return result;
    }

}
