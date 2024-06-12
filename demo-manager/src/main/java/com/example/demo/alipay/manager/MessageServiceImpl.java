package com.example.demo.alipay.manager;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.marketing.templatemessage.models.AlipayOpenAppMiniTemplatemessageSendResponse;
import com.example.demo.alipay.api.MessageService;
import com.example.demo.alipay.api.bo.BaseResult;
import org.springframework.stereotype.Service;

@Service("com.example.demo.alipay.manager.messageService")
public class MessageServiceImpl implements MessageService {

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
