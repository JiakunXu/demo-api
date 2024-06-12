package com.example.demo.alipay.manager;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.marketing.templatemessage.models.AlipayOpenAppMiniTemplatemessageSendResponse;
import com.example.demo.alipay.api.MessageService;
import com.example.demo.alipay.api.bo.BaseResult;
import com.example.demo.alipay.api.bo.message.Message;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("com.example.demo.alipay.manager.messageService")
public class MessageServiceImpl implements MessageService {

    @Override
    public Message verify(Map<String, String> parameters) {
        Message message = new Message();
        message.setNotifyId(parameters.get("notify_id"));
        message.setAppId(parameters.get("app_id"));
        message.setBizContent(parameters.get("biz_content"));
        message.setCharset(parameters.get("charset"));
        message.setMsgMethod(parameters.get("msg_method"));
        message.setSign(parameters.get("sign"));
        message.setSignType(parameters.get("sign_type"));
        message.setUtcTimestamp(parameters.get("utc_timestamp"));
        message.setVersion(parameters.get("version"));

        try {
            if (!Factory.Payment.Common().verifyNotify(parameters)) {
                throw new RuntimeException("验签失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return message;
    }

    @Override
    public BaseResult send(String toUserId, String formId, String userTemplateId, String page,
                           String data) {
        try {
            AlipayOpenAppMiniTemplatemessageSendResponse response = Factory.Marketing
                .TemplateMessage().send(toUserId, formId, userTemplateId, page, data);
            if (ResponseChecker.success(response)) {
                return JSON
                    .parseObject(
                        JSON.parseObject(response.getHttpBody(), JSONObject.class)
                            .getString("alipay_open_app_mini_templatemessage_send_response"),
                        BaseResult.class);
            } else {
                throw new RuntimeException("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
