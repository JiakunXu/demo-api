package com.example.demo.dingtalk.manager;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.demo.dingtalk.api.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Value("${dingtalk.app.key}")
    private String              appKey;

    @Value("${dingtalk.token}")
    private String              token;

    @Value("${dingtalk.encodingAesKey}")
    private String              encodingAesKey;

    @Override
    public Map<String, String> notify(String signature, String timeStamp, String nonce,
                                      String encrypt) {
        try {
            DingCallbackCrypto callbackCrypto = new DingCallbackCrypto(token, encodingAesKey,
                appKey);

            JSONObject data = JSON
                .parseObject(callbackCrypto.getDecryptMsg(signature, timeStamp, nonce, encrypt));

            String eventType = data.getString("EventType");

            // 审批实例开始、结束
            if ("bpms_instance_change".equals(eventType)) {
                change(data);
            }

            return callbackCrypto.getEncryptedMap("success");
        } catch (DingCallbackCrypto.DingTalkEncryptException e) {
            logger.error("handle", e);
        }

        return null;
    }

    private void change(JSONObject data) {
        String processCode = data.getString("processCode");

        String processInstanceId = data.getString("processInstanceId");
        String type = data.getString("type");
        String result = data.getString("result");

        // 审批正常结束（同意或拒绝）
        if ("finish".equals(type)) {
            if ("agree".equals(result)) {

            }

            if ("refuse".equals(result)) {

            }
        }

        // 审批终止（发起人撤销审批单）
        if ("terminate".equals(type)) {

        }
    }

}
