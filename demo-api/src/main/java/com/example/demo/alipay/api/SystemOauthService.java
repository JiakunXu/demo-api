package com.example.demo.alipay.api;

import com.example.demo.alipay.api.bo.token.OauthToken;

public interface SystemOauthService {

    OauthToken getToken(String code);

}
