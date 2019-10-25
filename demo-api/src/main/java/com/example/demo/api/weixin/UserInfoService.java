package com.example.demo.api.weixin;

import com.example.demo.api.weixin.ao.UserInfo;

/**
 * @author JiakunXu
 */
public interface UserInfoService {

    String HTTPS_USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=$ACCESS_TOKEN$&openid=$OPENID$&lang=$LANG$";

    /**
     * 获取用户基本信息(UnionID机制).
     *
     * @param accessToken 调用接口凭证.
     * @param openId 普通用户的标识，对当前公众号唯一.
     * @param lang 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语.
     * @return
     * @throws RuntimeException
     */
    UserInfo getUserInfo(String accessToken, String openId, String lang) throws RuntimeException;

}
