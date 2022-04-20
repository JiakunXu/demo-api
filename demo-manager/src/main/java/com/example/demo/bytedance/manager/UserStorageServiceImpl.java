package com.example.demo.bytedance.manager;

import com.alibaba.fastjson.JSON;
import com.example.demo.bytedance.api.UserStorageService;
import com.example.demo.bytedance.api.bo.user.KvItem;
import com.example.demo.bytedance.api.bo.user.UserStorage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JiakunXu
 */
@Service
public class UserStorageServiceImpl implements UserStorageService {

    private static final Logger logger = LoggerFactory.getLogger(UserStorageServiceImpl.class);

    @Override
    public void set(String accessToken, String openid,
                    List<KvItem> kvItemList) throws RuntimeException {
        if (StringUtils.isBlank(accessToken)) {
            throw new RuntimeException("access_token is null.");
        }

        if (StringUtils.isBlank(openid)) {
            throw new RuntimeException("openid is null.");
        }

        UserStorage userStorage = new UserStorage();
        userStorage.setAccessToken(accessToken);
        userStorage.setOpenid(openid);
        userStorage.setSigMethod("hmac_sha256");
        userStorage.setKvItemList(kvItemList);

        userStorage.setSignature(null);
    }

    @Override
    public void remove(String accessToken, String openid,
                       List<KvItem> kvList) throws RuntimeException {

    }

}
