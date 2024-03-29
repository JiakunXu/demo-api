package com.example.demo.weixin.manager;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSON;
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
    public String authorize(String appid, String redirectUrl, String scope,
                            String state) throws RuntimeException {
        if (StringUtils.isBlank(appid)) {
            throw new RuntimeException("appid 公众号的唯一标识 不能为空.");
        }

        if (StringUtils.isBlank(redirectUrl)) {
            throw new RuntimeException("redirect_url 授权后重定向的回调链接地址 不能为空.");
        }

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
        if (StringUtils.isBlank(appid)) {
            throw new RuntimeException("appid 公众号的唯一标识 不能为空.");
        }

        if (StringUtils.isBlank(secret)) {
            throw new RuntimeException("secret 公众号的appsecret 不能为空.");
        }

        if (StringUtils.isBlank(code)) {
            throw new RuntimeException("code code参数 不能为空.");
        }

        StringBuilder sb = new StringBuilder(Oauth2Service.HTTPS_ACCESS_TOKEN_URL);
        sb.append("&appid=").append(appid).append("&secret=").append(secret).append("&code=")
            .append(code);

        AccessToken accessToken;

        try {
            accessToken = JSON.parseObject(HttpUtil.get(sb.toString()), AccessToken.class);
        } catch (Exception e) {
            logger.error(sb.toString(), e);

            throw new RuntimeException(e);
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
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token cannot be null.");
        }

        if (StringUtils.isBlank(openid)) {
            throw new RuntimeException("openid cannot be null.");
        }

        lang = StringUtils.isBlank(lang) ? "zh_CN" : lang;

        UserInfo userInfo;

        try {
            userInfo = JSON.parseObject(HttpUtil
                .get(Oauth2Service.HTTPS_USER_INFO_URL.replace("$ACCESS_TOKEN$", accessToken)
                    .replace("$OPENID$", openid).replace("$LANG$", lang)),
                UserInfo.class);
        } catch (Exception e) {
            logger.error(accessToken + "&" + openid, e);

            throw new RuntimeException(e);
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
