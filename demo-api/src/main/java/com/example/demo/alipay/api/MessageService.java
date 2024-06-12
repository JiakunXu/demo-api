package com.example.demo.alipay.api;

import com.example.demo.alipay.api.bo.BaseResult;
import com.example.demo.alipay.api.bo.message.Message;

import java.util.Map;

public interface MessageService {

    Message verify(Map<String, String> parameters);

    BaseResult send(String toUserId, String formId, String userTemplateId, String page,
                    String data);

}
