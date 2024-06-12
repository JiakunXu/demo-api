package com.example.demo.alipay.api;

import com.example.demo.alipay.api.bo.BaseResult;

public interface MessageService {

    BaseResult send(String toUserId, String formId, String userTemplateId, String page,
                    String data);

}
