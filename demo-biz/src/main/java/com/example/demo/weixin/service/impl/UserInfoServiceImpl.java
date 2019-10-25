package com.example.demo.weixin.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.example.demo.api.weixin.UserInfoService;
import com.example.demo.api.weixin.ao.UserInfo;
import com.example.demo.framework.util.HttpUtil;

/**
 * @author JiakunXu
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

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
                HttpUtil.get(UserInfoService.HTTPS_USER_INFO_URL
                    .replace("$ACCESS_TOKEN$", accessToken.trim())
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
