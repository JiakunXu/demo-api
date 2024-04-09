package com.example.demo.bytedance.manager;

import com.alibaba.fastjson2.JSON;
import com.example.demo.bytedance.api.UserInfoService;
import com.example.demo.bytedance.api.bo.user.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author JiakunXu
 */
@Service("com.example.demo.bytedance.manager.userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Override
    public UserInfo getUserInfo(String appid, String encryptedData, String sessionKey,
                                String iv) throws RuntimeException {
        if (StringUtils.isBlank(appid)) {
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

        UserInfo userInfo;

        try {
            userInfo = JSON.parseObject(Decrypt.decrypt(encryptedData, sessionKey, iv),
                UserInfo.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (userInfo == null) {
            throw new RuntimeException("userInfo is null.");
        }

        // appid 不匹配
        if (!appid.equals(userInfo.getWatermark().getAppid())) {
            throw new RuntimeException("appId not equals.");
        }

        return userInfo;
    }

}
