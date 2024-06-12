package com.example.demo.alipay.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.alipay.api.AesService;
import com.example.demo.alipay.api.PhoneNumberService;
import com.example.demo.alipay.api.bo.user.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("com.example.demo.alipay.manager.phoneNumberService")
public class PhoneNumberServiceImpl implements PhoneNumberService {

    @Autowired
    private AesService aesService;

    @Override
    public PhoneNumber getPhoneNumber(String content, String sign) {
        try {
            if (!Signature.rsaCheck(content, sign)) {
                throw new RuntimeException("验签失败");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

        PhoneNumber phoneNumber = JSON.parseObject(aesService.decrypt(content),
            PhoneNumber.class);

        if (!"10000".equals(phoneNumber.getCode())) {
            throw new RuntimeException(
                "调用失败，原因：" + phoneNumber.getMsg() + "，" + phoneNumber.getSubMsg());
        }

        return phoneNumber;
    }

}
