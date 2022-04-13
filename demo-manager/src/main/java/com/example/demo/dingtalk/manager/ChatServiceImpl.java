package com.example.demo.dingtalk.manager;

import com.alibaba.fastjson.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiChatSendRequest;
import com.dingtalk.api.response.OapiChatSendResponse;
import com.example.demo.dingtalk.api.ChatService;
import com.example.demo.dingtalk.api.bo.msg.Msg;
import com.taobao.api.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service("chatService0")
public class ChatServiceImpl implements ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

    @Override
    public void send(String accessToken, String chatId, Msg msg) {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token is null.");
        }

        if (StringUtils.isBlank(chatId)) {
            throw new RuntimeException("chatid is null.");
        }

        if (msg == null) {
            throw new RuntimeException("msg is null.");
        }

        DingTalkClient client = new DefaultDingTalkClient(ChatService.HTTPS_CHAT_URL);

        OapiChatSendRequest request = new OapiChatSendRequest();
        request.setChatid(chatId);
        request.setMsg(JSON.toJSONString(msg));

        OapiChatSendResponse response = null;

        try {
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            logger.error(JSON.toJSONString(request), e);
            throw new RuntimeException("execute", e);
        }

        if (response == null) {
            throw new RuntimeException("response is null.");
        }

        String errCode = response.getErrorCode();

        if (StringUtils.isNotBlank(errCode) && !"0".equals(errCode)) {
            throw new RuntimeException(response.getErrmsg());
        }
    }

}
