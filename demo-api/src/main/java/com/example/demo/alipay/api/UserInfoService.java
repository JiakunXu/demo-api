package com.example.demo.alipay.api;

import com.example.demo.alipay.api.bo.user.UserInfo;

public interface UserInfoService {

    UserInfo getUserInfo(String authToken);

}
