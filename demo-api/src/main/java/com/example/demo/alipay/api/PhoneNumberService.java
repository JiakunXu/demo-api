package com.example.demo.alipay.api;

import com.example.demo.alipay.api.bo.user.PhoneNumber;

public interface PhoneNumberService {

    PhoneNumber getPhoneNumber(String content, String sign);

}
