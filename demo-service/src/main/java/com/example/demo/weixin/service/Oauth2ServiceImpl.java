package com.example.demo.weixin.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.example.demo.weixin.api.Oauth2Service;
import com.example.demo.weixin.api.bo.sns.AccessToken;
import com.example.demo.weixin.api.bo.sns.UserInfo;
import com.example.demo.framework.util.HttpUtil;

/**
 * @author JiakunXu
 */
@Service
public class Oauth2ServiceImpl implements Oauth2Service {

    private static final Logger logger = LoggerFactory.getLogger(Oauth2ServiceImpl.class);

    @Override
    public String authorize(String appId, String redirectUrl, String scope,
                            String state) throws RuntimeException {
        if (StringUtils.isBlank(appId)) {
            throw new RuntimeException("appid 公众号的唯一标识 不能为空.");
        }

        if (StringUtils.isBlank(redirectUrl)) {
            throw new RuntimeException("redirect_url 授权后重定向的回调链接地址 不能为空.");
        }

        StringBuilder sb = new StringBuilder(
            "snsapi_login".equals(scope) ? Oauth2Service.HTTPS_QRCONNECT_URL
                : Oauth2Service.HTTPS_AUTHORIZE_URL);

        sb.append("?appid=").append(appId).append("&redirect_uri=").append(redirectUrl)
            .append("&response_type=code&scope=").append(scope).append("&state=").append(state)
            .append("#wechat_redirect");

        return sb.toString();
    }

    @Override
    public AccessToken getAccessToken(String appId, String appSecret,
                                      String code) throws RuntimeException {
        if (StringUtils.isBlank(appId)) {
            throw new RuntimeException("appid 公众号的唯一标识 不能为空.");
        }

        if (StringUtils.isBlank(appSecret)) {
            throw new RuntimeException("secret 公众号的appsecret 不能为空.");
        }

        if (StringUtils.isBlank(code)) {
            throw new RuntimeException("code code参数 不能为空.");
        }

        StringBuilder sb = new StringBuilder(Oauth2Service.HTTPS_ACCESS_TOKEN_URL);
        sb.append("&appid=").append(appId).append("&secret=").append(appSecret).append("&code=")
            .append(code);

        AccessToken accessToken = null;

        try {
            accessToken = JSON.parseObject(HttpUtil.get(sb.toString()), AccessToken.class);
        } catch (Exception e) {
            logger.error(sb.toString(), e);

            throw new RuntimeException("HttpUtil error.");
        }

        if (accessToken == null) {
            throw new RuntimeException("access_token is null.");
        }

        String errCode = accessToken.getErrCode();
        if (StringUtils.isNotBlank(errCode)) {
            logger.error(JSON.toJSONString(accessToken));

            throw new RuntimeException(accessToken.getErrMsg());
        }

        return accessToken;
    }

    @Override
    public UserInfo getUserInfo(String accessToken, String openId,
                                String lang) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(openId)) {
            throw new RuntimeException("openid cannot be null.");
        }

        lang = StringUtils.isBlank(lang) ? "zh_CN" : lang;

        UserInfo userInfo = null;

        try {
            userInfo = JSON.parseObject(
                HttpUtil.get(
                    Oauth2Service.HTTPS_USER_INFO_URL.replace("$ACCESS_TOKEN$", accessToken.trim())
                        .replace("$OPENID$", openId.trim()).replace("$LANG$", lang.trim())),
                UserInfo.class);
        } catch (Exception e) {
            logger.error(accessToken + "&" + openId, e);

            throw new RuntimeException("HttpUtil error.", e);
        }

        if (userInfo == null) {
            throw new RuntimeException("userinfo is null.");
        }

        String errCode = userInfo.getErrCode();
        if (StringUtils.isNotBlank(errCode) && !"0".equals(errCode)) {
            throw new RuntimeException(userInfo.getErrMsg());
        }

        return userInfo;
    }
}
