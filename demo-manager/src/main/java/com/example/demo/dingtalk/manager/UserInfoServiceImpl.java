package com.example.demo.dingtalk.manager;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.request.OapiV2UserGetbymobileRequest;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.dingtalk.api.response.OapiV2UserGetbymobileResponse;
import com.example.demo.dingtalk.api.UserInfoService;
import com.example.demo.dingtalk.api.bo.UserInfo;
import com.example.demo.framework.util.BeanUtil;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service("com.example.demo.dingtalk.manager.userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Override
    public UserInfo getUserInfo(String accessToken, String userid) throws RuntimeException {
        DingTalkClient client = new DefaultDingTalkClient(HTTPS_GET_URL);

        OapiV2UserGetRequest request = new OapiV2UserGetRequest();
        request.setUserid(userid);

        OapiV2UserGetResponse response;

        try {
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            log.error("{}", request, e);
            throw new RuntimeException(e.getMessage(), e);
        }

        if (response == null) {
            throw new RuntimeException("response is null.");
        }

        Long errcode = response.getErrcode();

        if (errcode != null && errcode != 0) {
            throw new RuntimeException(response.getErrmsg());
        }

        return BeanUtil.copy(response.getResult(), UserInfo.class);
    }

    @Override
    public String getUserid(String accessToken, String mobile) throws RuntimeException {
        DingTalkClient client = new DefaultDingTalkClient(HTTPS_GET_BY_MOBILE_URL);

        OapiV2UserGetbymobileRequest request = new OapiV2UserGetbymobileRequest();
        request.setMobile(mobile);
        request.setSupportExclusiveAccountSearch(Boolean.FALSE);

        OapiV2UserGetbymobileResponse response;

        try {
            response = client.execute(request, accessToken);
        } catch (ApiException e) {
            log.error("{}", request, e);
            throw new RuntimeException(e.getMessage(), e);
        }

        if (response == null) {
            throw new RuntimeException("response is null.");
        }

        Long errcode = response.getErrcode();

        if (errcode != null && errcode != 0) {
            throw new RuntimeException(response.getErrmsg());
        }

        return response.getResult().getUserid();
    }

}
