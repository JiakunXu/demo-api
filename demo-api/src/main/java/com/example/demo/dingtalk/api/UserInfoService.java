package com.example.demo.dingtalk.api;

import com.example.demo.dingtalk.api.bo.UserInfo;

public interface UserInfoService {

    String HTTPS_GET_URL           = "https://oapi.dingtalk.com/topapi/v2/user/get";

    String HTTPS_GET_BY_MOBILE_URL = "https://oapi.dingtalk.com/topapi/v2/user/getbymobile";

    UserInfo getUserInfo(String accessToken, String userid) throws RuntimeException;

    String getUserid(String accessToken, String mobile) throws RuntimeException;

}
