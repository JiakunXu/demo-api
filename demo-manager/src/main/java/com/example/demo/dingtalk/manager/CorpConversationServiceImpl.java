package com.example.demo.dingtalk.manager;

import com.example.demo.dingtalk.api.CorpConversationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSON;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.taobao.api.ApiException;

/**
 * @author JiakunXu
 */
@Service
public class CorpConversationServiceImpl implements CorpConversationService {

    private static final Logger logger = LoggerFactory.getLogger(CorpConversationServiceImpl.class);

    @Override
    public void send(String accessToken, Long agentId, String useridList, String title,
                     String text) {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token is null.");
        }

        if (agentId == null) {
            throw new RuntimeException("agent_id is null.");
        }

        if (StringUtils.isBlank(useridList)) {
            throw new RuntimeException("userid_list is null.");
        }

        DingTalkClient client = new DefaultDingTalkClient(CorpConversationService.HTTPS_SEND_URL);

        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(agentId);
        request.setUseridList(useridList);

        OapiMessageCorpconversationAsyncsendV2Request.Msg mgs = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        mgs.setMsgtype("markdown");
        mgs.setMarkdown(new OapiMessageCorpconversationAsyncsendV2Request.Markdown());
        mgs.getMarkdown().setText(text);
        mgs.getMarkdown().setTitle(title);
        request.setMsg(mgs);

        OapiMessageCorpconversationAsyncsendV2Response response;

        try {
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            logger.error(JSON.toJSONString(request), e);
            throw new RuntimeException(e);
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
