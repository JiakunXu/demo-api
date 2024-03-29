package com.example.demo.dingtalk.service;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.demo.dingtalk.api.DingtalkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DingtalkServiceImpl implements DingtalkService {

    private static final Logger logger = LoggerFactory.getLogger(DingtalkServiceImpl.class);

    @Value("${dingtalk.app.key}")
    private String              appKey;

    @Value("${dingtalk.token}")
    private String              token;

    @Value("${dingtalk.encodingAesKey}")
    private String              encodingAesKey;

    @Override
    public Map<String, String> callback(String signature, String timeStamp, String nonce,
                                        String encrypt) {
        try {
            DingCallbackCrypto callbackCrypto = new DingCallbackCrypto(token, encodingAesKey,
                appKey);

            JSONObject object = JSON
                .parseObject(callbackCrypto.getDecryptMsg(signature, timeStamp, nonce, encrypt));

            String eventType = object.getString("EventType");

            // 审批实例开始、结束
            if ("bpms_instance_change".equals(eventType)) {
                change(object);
            }

            return callbackCrypto.getEncryptedMap("success");
        } catch (DingCallbackCrypto.DingTalkEncryptException e) {
            logger.error("handle", e);
        }

        return null;
    }

    private void change(JSONObject object) {
        String processCode = object.getString("processCode");

        String processInstanceId = object.getString("processInstanceId");
        String type = object.getString("type");
        String result = object.getString("result");

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
