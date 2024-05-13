package com.example.demo.weixin.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.weixin.api.Oauth2Service;
import com.example.demo.weixin.api.bo.sns.AccessToken;
import com.example.demo.weixin.api.bo.sns.UserInfo;
import com.example.demo.framework.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author JiakunXu
 */
@Service
public class Oauth2ServiceImpl implements Oauth2Service {

    private static final Logger logger = LoggerFactory.getLogger(Oauth2ServiceImpl.class);

    @Override
    public String authorize(String appid, String redirectUrl, String scope,
                            String state) throws RuntimeException {
        StringBuilder sb = new StringBuilder(
            "snsapi_login".equals(scope) ? Oauth2Service.HTTPS_QRCONNECT_URL
                : Oauth2Service.HTTPS_AUTHORIZE_URL);

        sb.append("?appid=").append(appid).append("&redirect_uri=").append(redirectUrl)
            .append("&response_type=code&scope=").append(scope).append("&state=").append(state)
            .append("#wechat_redirect");

        return sb.toString();
    }

    @Override
    public AccessToken getAccessToken(String appid, String secret,
                                      String code) throws RuntimeException {
        AccessToken accessToken;

        try {
            accessToken = JSON.parseObject(
                HttpUtil.get(MessageFormat.format(HTTPS_ACCESS_TOKEN_URL, appid, secret, code)),
                AccessToken.class);
        } catch (Exception e) {
            logger.error(appid + "&" + secret + "&" + code, e);
            throw new RuntimeException(e.getMessage(), e);
        }

        if (accessToken == null) {
            throw new RuntimeException("access_token is null.");
        }

        if (accessToken.getErrCode() != 0) {
            logger.error(accessToken.toString());
            throw new RuntimeException(accessToken.getErrMsg());
        }

        return accessToken;
    }

    @Override
    public UserInfo getUserInfo(String accessToken, String openid,
                                String lang) throws RuntimeException {
        UserInfo userInfo;

        try {
            userInfo = JSON.parseObject(HttpUtil.get(MessageFormat.format(HTTPS_USER_INFO_URL,
                accessToken, openid, StringUtils.isBlank(lang) ? "zh_CN" : lang)), UserInfo.class);
        } catch (Exception e) {
            logger.error(accessToken + "&" + openid, e);
            throw new RuntimeException(e.getMessage(), e);
        }

        if (userInfo == null) {
            throw new RuntimeException("userinfo is null.");
        }

        if (userInfo.getErrCode() != 0) {
            throw new RuntimeException(userInfo.getErrMsg());
        }

        return userInfo;
    }

}
