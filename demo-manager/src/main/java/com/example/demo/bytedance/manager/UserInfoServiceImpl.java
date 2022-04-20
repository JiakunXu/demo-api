package com.example.demo.bytedance.manager;

import com.alibaba.fastjson.JSON;
import com.example.demo.bytedance.api.UserInfoService;
import com.example.demo.bytedance.api.bo.user.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Override
    public UserInfo getUserInfo(String appId, String encryptedData, String sessionKey,
                                String iv) throws RuntimeException {
        if (StringUtils.isBlank(appId)) {
            throw new RuntimeException("appid is null.");
        }

        if (StringUtils.isBlank(encryptedData)) {
            throw new RuntimeException("encryptedData is null.");
        }

        if (StringUtils.isBlank(sessionKey)) {
            throw new RuntimeException("sessionKey is null.");
        }

        if (StringUtils.isBlank(iv)) {
            throw new RuntimeException("iv is null.");
        }

        UserInfo userInfo = null;

        try {
            userInfo = JSON.parseObject(Decrypt.decrypt(encryptedData, sessionKey, iv),
                UserInfo.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (userInfo == null) {
            throw new RuntimeException("userInfo is null.");
        }

        // appId 不匹配
        if (!appId.equals(userInfo.getWatermark().getAppId())) {
            throw new RuntimeException("appId not equals.");
        }

        return userInfo;
    }

}
