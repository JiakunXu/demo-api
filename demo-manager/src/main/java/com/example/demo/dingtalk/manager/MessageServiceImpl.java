package com.example.demo.dingtalk.manager;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiMessageCorpconversationRecallRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiMessageCorpconversationRecallResponse;
import com.example.demo.dingtalk.api.MessageService;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("com.example.demo.dingtalk.manager.messageService")
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

    @Override
    public Long send(String accessToken, Long agentId, String useridList, String mediaId) {
        DingTalkClient client = new DefaultDingTalkClient(HTTPS_SEND_URL);

        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(agentId);
        request.setUseridList(useridList);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("file");
        msg.setFile(new OapiMessageCorpconversationAsyncsendV2Request.File());
        msg.getFile().setMediaId(mediaId);
        request.setMsg(msg);

        OapiMessageCorpconversationAsyncsendV2Response response;

        try {
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            logger.error(JSON.toJSONString(request), e);
            throw new RuntimeException(e.getMessage(), e);
        }

        if (response == null) {
            throw new RuntimeException("response is null.");
        }

        Long errcode = response.getErrcode();

        if (errcode != null && errcode != 0) {
            throw new RuntimeException(response.getErrmsg());
        }

        return response.getTaskId();
    }

    @Override
    public Long send(String accessToken, Long agentId, String useridList, String title,
                     String text) {
        DingTalkClient client = new DefaultDingTalkClient(HTTPS_SEND_URL);

        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(agentId);
        request.setUseridList(useridList);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("markdown");
        msg.setMarkdown(new OapiMessageCorpconversationAsyncsendV2Request.Markdown());
        msg.getMarkdown().setText(text);
        msg.getMarkdown().setTitle(title);
        request.setMsg(msg);

        OapiMessageCorpconversationAsyncsendV2Response response;

        try {
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            logger.error(JSON.toJSONString(request), e);
            throw new RuntimeException(e.getMessage(), e);
        }

        if (response == null) {
            throw new RuntimeException("response is null.");
        }

        Long errcode = response.getErrcode();

        if (errcode != null && errcode != 0) {
            throw new RuntimeException(response.getErrmsg());
        }

        return response.getTaskId();
    }

    @Override
    public void recall(String accessToken, Long agentId, Long msgTaskId) {
        DingTalkClient client = new DefaultDingTalkClient(HTTPS_RECALL_URL);

        OapiMessageCorpconversationRecallRequest request = new OapiMessageCorpconversationRecallRequest();
        request.setAgentId(agentId);
        request.setMsgTaskId(msgTaskId);

        OapiMessageCorpconversationRecallResponse response;

        try {
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            logger.error(JSON.toJSONString(request), e);
            throw new RuntimeException(e.getMessage(), e);
        }

        if (response == null) {
            throw new RuntimeException("response is null.");
        }

        Long errcode = response.getErrcode();

        if (errcode != null && errcode != 0) {
            throw new RuntimeException(response.getErrmsg());
        }
    }

}
